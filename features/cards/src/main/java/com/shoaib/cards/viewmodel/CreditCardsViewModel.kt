package com.shoaib.cards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.cards.data.CardResult
import com.shoaib.cards.data.credit.repository.CreditCardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditCardsViewModel @Inject constructor(
    private val repository: CreditCardsRepository
) : ViewModel() {

    // Expose credit cards stream directly from repository cache
    val cards = repository.getCardsStream()

    private val _uiState = MutableStateFlow<CreditCardsUiState>(CreditCardsUiState.Loading)
    val uiState: StateFlow<CreditCardsUiState> = _uiState.asStateFlow()

    init {
        // Observe cards stream and update UI state
        viewModelScope.launch {
            cards.collect { cardList ->
                if (cardList.isNotEmpty()) {
                    _uiState.value = CreditCardsUiState.Success(cardList)
                }
            }
        }
        refreshCards()
    }

    fun refreshCards() {
        viewModelScope.launch {
            _uiState.value = CreditCardsUiState.Loading

            when (val result = repository.refreshCards()) {
                is CardResult.Success -> {
                    // Cards are now in cache, UI will automatically update via cards Flow observer above
                }
                is CardResult.Error -> {
                    _uiState.value = CreditCardsUiState.Error(result.message)
                }
                is CardResult.Loading -> {
                    _uiState.value = CreditCardsUiState.Loading
                }
            }
        }
    }
}
