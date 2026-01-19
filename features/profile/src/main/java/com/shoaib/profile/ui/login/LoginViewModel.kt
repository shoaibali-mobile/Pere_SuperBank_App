package com.shoaib.profile.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.api.AuthRepository
import com.shoaib.api.model.AuthUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Login screen.
 * 
 * RESPONSIBILITIES:
 * 1. Hold UI state (LoginUiState)
 * 2. Handle user actions (login button click)
 * 3. Call repository to perform login
 * 4. Update state based on repository response
 * 
 * WHY HiltViewModel?
 * - @HiltViewModel enables Hilt to inject dependencies
 * - Automatically scoped to ViewModel lifecycle
 * - Survives configuration changes (screen rotation)
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository  // ← Injected via Hilt
) : ViewModel() {


    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    


    fun login(email: String, password: String) {
        // Validate input
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error("Email and password cannot be empty")
            return
        }
        
        // Update state to Loading
        _uiState.value = LoginUiState.Loading
        
        // Launch coroutine in ViewModel scope
        viewModelScope.launch {
            // Call repository (this is suspend function)
            val result = authRepository.login(email, password)
            
            // Handle result
            result.onSuccess { user ->
                // Success - update state with user
                _uiState.value = LoginUiState.Success(user)
            }.onFailure { error ->
                // Error - update state with error message
                _uiState.value = LoginUiState.Error(error.message)
            }
        }
    }
    
    /**
     * Reset state to Initial (useful for error recovery)
     */
    fun resetState() {
        _uiState.value = LoginUiState.Initial
    }
}
