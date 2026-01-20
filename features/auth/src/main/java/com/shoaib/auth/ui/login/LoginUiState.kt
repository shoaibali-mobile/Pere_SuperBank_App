package com.shoaib.auth.ui.login

import com.shoaib.api.model.AuthUser

sealed interface LoginUiState {
    data object Initial : LoginUiState
    data object Loading : LoginUiState
    data class Success(val user: AuthUser) : LoginUiState
    data class Error(val message: String) : LoginUiState
}
