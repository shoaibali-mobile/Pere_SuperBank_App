package com.shoaib.cards.model.debit


sealed interface DebitCardsUiState {
    object Loading : DebitCardsUiState
    data class Success(val cards: List<DebitCardDto>) : DebitCardsUiState
    data class Error(val message: String) : DebitCardsUiState
}