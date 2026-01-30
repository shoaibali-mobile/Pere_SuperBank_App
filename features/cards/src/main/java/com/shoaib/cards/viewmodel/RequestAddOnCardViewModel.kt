package com.shoaib.cards.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.cards.data.CardResult
import com.shoaib.cards.data.credit.repository.CreditCardsRepository
import com.shoaib.cards.model.CreditCardDto
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

@HiltViewModel
class RequestAddOnCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CreditCardsRepository
) : ViewModel() {

    private val cardId: String = checkNotNull(savedStateHandle["cardId"])

    private val _card = MutableStateFlow<CreditCardDto?>(null)
    val card: StateFlow<CreditCardDto?> = _card.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _snackbarEvent = MutableSharedFlow<SnackbarEvent>()
    val snackbarEvent: SharedFlow<SnackbarEvent> = _snackbarEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            repository.getCardById(cardId)
                .catch { _card.value = null }
                .collect { _card.value = it }
        }
    }

    fun submitRequest(
        customerID: String,
        nameOnCard: String,
        dateOfBirth: String,
        relationship: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = repository.requestAddOnCard(
                    cardId = cardId,
                    customerID = customerID,
                    nameOnCard = nameOnCard,
                    dateOfBirth = dateOfBirth,
                    relationship = relationship
                )) {
                    is CardResult.Success -> {
                        _snackbarEvent.emit(
                            SnackbarEvent.Success(
                                result.data?.let { "Add-on card request submitted successfully" }
                                    ?: "Request submitted"
                            )
                        )
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
