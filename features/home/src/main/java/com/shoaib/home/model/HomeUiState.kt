package com.shoaib.home.model

import com.shoaib.api.model.AuthUser

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val user: AuthUser, val greeting: String) : HomeUiState
}
