package com.shoaib.cards.viewmodel

import com.shoaib.cards.model.CreditCardDto

sealed interface CardsUiState {
    object Loading : CardsUiState
    data class Success(val cards: List<CreditCardDto>) : CardsUiState
    data class Error(val message: String) : CardsUiState
}
