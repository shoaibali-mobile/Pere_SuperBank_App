package com.shoaib.auth.ui.pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.api.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _uistate = MutableStateFlow<PinUiState>(PinUiState.Loading)
    val uistate = _uistate.asStateFlow()

    init {
        checkPinStatus()
    }


    private fun checkPinStatus() {
        viewModelScope.launch {
            // If user has a PIN, we verify. If not, we let them set one up.
            val hasPin = authRepository.hasPin()
            _uistate.value = PinUiState.Content(
                mode = if (hasPin) PinMode.Verify else PinMode.Setup
            )
        }
    }


    fun onDigitEntered(digit: String) {
        val currentState = _uistate.value as? PinUiState.Content ?: return

        if (currentState.enteredPin.length < 4) {
            val newPin = currentState.enteredPin + digit
            _uistate.value = currentState.copy(enteredPin = newPin, error = null)

            // If we reached 4 digits, automatically submit
            if (newPin.length == 4) {
                validatePin(newPin, currentState.mode)
            }
        }
    }

    fun onBackspace() {
        val currentState = _uistate.value as? PinUiState.Content ?: return
        if (currentState.enteredPin.isNotEmpty()) {
            _uistate.value = currentState.copy(
                enteredPin = currentState.enteredPin.dropLast(1),
                error = null
            )
        }
    }


    private fun validatePin(pin: String, mode: PinMode) {
        viewModelScope.launch {
            if (mode == PinMode.Verify) {
                val result = authRepository.verifyPin(pin)
                result.onSuccess { isValid ->
                    if (isValid) {
                        _uistate.value = PinUiState.Success
                    } else {
                        // Reset pin and show error
                        _uistate.value = (_uistate.value as PinUiState.Content).copy(
                            enteredPin = "",
                            error = "Incorrect PIN"
                        )
                    } }.onFailure {
                    _uistate.value = (_uistate.value as PinUiState.Content).copy(
                        enteredPin = "",
                        error = "Error verifying PIN"
                    )
                }
            } else {
                // Setup Mode Logic (Simplified for now)
                authRepository.setPin(pin)
                _uistate.value = PinUiState.Success
            }
        }
    }

}