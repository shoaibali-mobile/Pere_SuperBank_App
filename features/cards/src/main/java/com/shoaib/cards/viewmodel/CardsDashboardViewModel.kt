package com.shoaib.cards.viewmodel

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
class CardsDashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _displayName = MutableStateFlow("there")
    val displayName: StateFlow<String> = _displayName.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.getCurrentUser().collect { authUser ->
                _displayName.value = authUser?.name?.takeIf { it.isNotBlank() } ?: "there"
            }
        }
    }
}
