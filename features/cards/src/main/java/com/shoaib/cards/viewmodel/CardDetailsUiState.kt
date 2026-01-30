package com.shoaib.cards.viewmodel

sealed interface CardDetailsUiState {
    object Loading : CardDetailsUiState
    data class Success(
        val cards: List<CardDetailsUiModel>,
        val initialIndex: Int
    ) : CardDetailsUiState
    data class Error(val message: String) : CardDetailsUiState
}
