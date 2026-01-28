package com.shoaib.cards.viewmodel

import com.shoaib.cards.model.CreditCardDto

sealed interface CardDetailsUiState {
    object Loading : CardDetailsUiState
    data class Success(
        val cards: List<CreditCardDto>,
        val initialIndex: Int
    ) : CardDetailsUiState
    data class Error(val message: String) : CardDetailsUiState
}
