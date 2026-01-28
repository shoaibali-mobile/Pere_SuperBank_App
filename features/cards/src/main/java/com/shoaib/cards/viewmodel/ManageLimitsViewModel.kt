package com.shoaib.cards.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.cards.data.CardResult
import com.shoaib.cards.data.repository.CardsRepository
import com.shoaib.cards.model.managelimit.CardLimitData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ManageLimitsUiState {
    object Loading : ManageLimitsUiState
    data class Success(val limits: CardLimitData) : ManageLimitsUiState
    data class Error(val message: String) : ManageLimitsUiState
}

@HiltViewModel
class ManageLimitsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CardsRepository
) : ViewModel() {

    private val cardId: String = checkNotNull(savedStateHandle["cardId"])

    private val _uiState = MutableStateFlow<ManageLimitsUiState>(ManageLimitsUiState.Loading)
    val uiState: StateFlow<ManageLimitsUiState> = _uiState.asStateFlow()

    init {
        fetchLimits()
    }

    private fun fetchLimits() {
        viewModelScope.launch {
            _uiState.value = ManageLimitsUiState.Loading
            when (val result = repository.getCardLimits(cardId)) {
                is CardResult.Success -> {
                    _uiState.value = ManageLimitsUiState.Success(result.data)
                }
                is CardResult.Error -> {
                    _uiState.value = ManageLimitsUiState.Error(result.message)
                }
                is CardResult.Loading -> {
                    // Handled by initial state
                }
            }
        }
    }

    fun updateLimits(updatedLimits: CardLimitData) {
        viewModelScope.launch {
            when (val result = repository.updateCardLimits(cardId, updatedLimits)) {
                is CardResult.Success -> {
                    // Re-fetch limits to ensure we have the full, correct data from the server
                    fetchLimits()
                }
                is CardResult.Error -> {
                     _uiState.value = ManageLimitsUiState.Error(result.message)
                }
                is CardResult.Loading -> {}
            }
        }
    }
}
