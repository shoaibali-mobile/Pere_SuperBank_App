package com.shoaib.cards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.cards.data.CardResult
import com.shoaib.cards.data.repository.CardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val repository: CardsRepository
) : ViewModel() {

    // Expose cards stream directly from repository cache
    val cards = repository.getCardsStream()

    private val _uiState = MutableStateFlow<CardsUiState>(CardsUiState.Loading)
    val uiState: StateFlow<CardsUiState> = _uiState.asStateFlow()

    init {
        // Observe cards stream and update UI state
        viewModelScope.launch {
            cards.collect { cardList ->
                if (cardList.isNotEmpty()) {
                    _uiState.value = CardsUiState.Success(cardList)
                }
            }
        }
        refreshCards()
    }

    fun refreshCards() {
        viewModelScope.launch {
            _uiState.value = CardsUiState.Loading
            
            when (val result = repository.refreshCards()) {
                is CardResult.Success -> {
                    // Cards are now in cache, UI will automatically update via cards Flow observer above
                }
                is CardResult.Error -> {
                    _uiState.value = CardsUiState.Error(result.message)
                }
                is CardResult.Loading -> {
                    _uiState.value = CardsUiState.Loading
                }
            }
        }
    }
}
