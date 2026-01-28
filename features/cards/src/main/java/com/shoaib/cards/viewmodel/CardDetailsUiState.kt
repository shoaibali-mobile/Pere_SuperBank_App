package com.shoaib.cards.viewmodel

import com.shoaib.cards.model.CreditCardDto

sealed interface CardDetailsUiState {
    object Loading : CardDetailsUiState
    data class Success(val card: CreditCardDto) : CardDetailsUiState
    data class Error(val message: String) : CardDetailsUiState
}
