package com.shoaib.cards.viewmodel

import com.shoaib.cards.model.CreditCardDto

sealed interface CreditCardsUiState {
    object Loading : CreditCardsUiState
    data class Success(val cards: List<CreditCardDto>) : CreditCardsUiState
    data class Error(val message: String) : CreditCardsUiState
}
