package com.shoaib.auth.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.api.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        android.util.Log.d("LoginDebug", "ViewModel: Attempting login with email: $email")
        if (email.isBlank() || password.isBlank()) {
            android.util.Log.e("LoginDebug", "ViewModel: Login failed - Empty credentials")
            _uiState.value = LoginUiState.Error("Email and password cannot be empty")
            return
        }

        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {
            android.util.Log.d("LoginDebug", "ViewModel: Calling AuthRepository.login...")
            val result = authRepository.login(email, password)
            
            result.onSuccess { user ->
                android.util.Log.d("LoginDebug", "ViewModel: Login SUCCESS! User: ${user.name}")
                _uiState.value = LoginUiState.Success(user)
            }.onFailure { error ->
                android.util.Log.e("LoginDebug", "ViewModel: Login FAILED! Error: ${error.message}", )
                _uiState.value = LoginUiState.Error(error.message)
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Initial
    }
}
