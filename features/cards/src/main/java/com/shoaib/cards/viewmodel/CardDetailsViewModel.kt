package com.shoaib.cards.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.cards.data.repository.CardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CardDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: CardsRepository
) : ViewModel() {

    // Get cardId from SavedStateHandle
    // With type-safe navigation, route parameters are available via SavedStateHandle
    // The key matches the parameter name in the route data class
    private val cardId: String = checkNotNull(savedStateHandle["cardId"])

    // Observe the card from repository cache by ID
    val uiState: StateFlow<CardDetailsUiState> = repository.getCardById(cardId)
        .map { card ->
            if (card != null) {
                CardDetailsUiState.Success(card)
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
