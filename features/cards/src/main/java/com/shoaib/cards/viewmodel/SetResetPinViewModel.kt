package com.shoaib.cards.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.cards.data.repository.CardsRepository
import com.shoaib.cards.model.CreditCardDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetResetPinViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CardsRepository
) : ViewModel() {

    private val cardId: String = checkNotNull(savedStateHandle["cardId"])

    private val _card = MutableStateFlow<CreditCardDto?>(null)
    val card: StateFlow<CreditCardDto?> = _card.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getCardById(cardId)
                .catch { _card.value = null }
                .collect { _card.value = it }
        }
    }
}
