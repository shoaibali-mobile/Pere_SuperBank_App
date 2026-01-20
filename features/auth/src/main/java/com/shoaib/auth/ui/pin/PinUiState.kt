package com.shoaib.auth.ui.pin


sealed interface PinUiState {
    data object Loading : PinUiState

    data class Content(
        val enteredPin: String = "",
        val mode: PinMode = PinMode.Verify,
        val error: String? = null
    ) : PinUiState

    data object Success : PinUiState
}

enum class PinMode {
    Setup,
    Verify
}