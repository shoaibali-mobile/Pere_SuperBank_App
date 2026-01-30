package com.shoaib.cards.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.cards.data.credit.repository.CreditCardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CardDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: CreditCardsRepository
) : ViewModel() {

    // Get cardId from SavedStateHandle
    // With type-safe navigation, route parameters are available via SavedStateHandle
    // The key matches the parameter name in the route data class
    private val cardId: String = checkNotNull(savedStateHandle["cardId"])

    // Observe all cards to support swiping
    val uiState: StateFlow<CardDetailsUiState> = repository.getCardsStream()
        .map { cards ->
            val index = cards.indexOfFirst { it.id == cardId }
            if (index != -1) {
                CardDetailsUiState.Success(cards, index)
            } else {
                CardDetailsUiState.Error("Card not found")
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CardDetailsUiState.Loading
        )
}
