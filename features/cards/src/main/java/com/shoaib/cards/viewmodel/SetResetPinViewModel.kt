package com.shoaib.cards.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.cards.data.CardResult
import com.shoaib.cards.data.credit.repository.CreditCardsRepository
import com.shoaib.cards.data.debit.repository.DebitCardsRepository
import com.shoaib.cards.model.CreditCardDto
import com.shoaib.cards.model.debit.DebitCardDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SnackbarEvent {
    data class Success(val message: String) : SnackbarEvent()
    data class Error(val message: String) : SnackbarEvent()
}




@HiltViewModel
class SetResetPinViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val creditRepository: CreditCardsRepository,
    private val debitRepository: DebitCardsRepository
) : ViewModel() {

    private val _snackbarEvent = MutableSharedFlow<SnackbarEvent>()
    val snackbarEvent: SharedFlow<SnackbarEvent> = _snackbarEvent.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val cardId: String = checkNotNull(savedStateHandle["cardId"])
    private val isDebit: Boolean = savedStateHandle.get<Boolean>("isDebit") ?: false

    private val _creditCard = MutableStateFlow<CreditCardDto?>(null)
    val creditCard: StateFlow<CreditCardDto?> = _creditCard.asStateFlow()

    private val _debitCard = MutableStateFlow<DebitCardDto?>(null)
    val debitCard: StateFlow<DebitCardDto?> = _debitCard.asStateFlow()

    init {
        viewModelScope.launch {
            if (isDebit) {
                debitRepository.getDebitCardById(cardId)
                    .catch { _debitCard.value = null }
                    .collect { _debitCard.value = it }
            } else {
                creditRepository.getCardById(cardId)
                    .catch { _creditCard.value = null }
                    .collect { _creditCard.value = it }
            }
        }
    }


    fun submitPin(newPin: String, confirmPin: String, termsAccepted: Boolean) {
        viewModelScope.launch {
            if (newPin != confirmPin) {
                _snackbarEvent.emit(SnackbarEvent.Error("PINs do not match"))
                return@launch
            }
            _isLoading.value = true
            try {
                val result = if (isDebit) {
                    debitRepository.setResetPin(cardId, newPin, confirmPin, termsAccepted)
                } else {
                    creditRepository.setResetPin(cardId, newPin, confirmPin, termsAccepted)
                }
                when (result) {
                    is CardResult.Success -> {
                        _snackbarEvent.emit(SnackbarEvent.Success("PIN updated successfully"))
                    }
                    is CardResult.Error -> {
                        _snackbarEvent.emit(SnackbarEvent.Error(result.message))
                    }
                    is CardResult.Loading -> { }
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
