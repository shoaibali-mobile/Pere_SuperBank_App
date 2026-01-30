package com.shoaib.cards.viewmodel.debit


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.cards.data.CardResult
import com.shoaib.cards.data.debit.repository.DebitCardsRepository
import com.shoaib.cards.model.debit.DebitCardsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebitCardsViewModel @Inject constructor(
    private val repository: DebitCardsRepository
) : ViewModel() {

    val cards = repository.getDebitCardsStream()

    private val _uiState = MutableStateFlow<DebitCardsUiState>(DebitCardsUiState.Loading)
    val uiState: StateFlow<DebitCardsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            cards.collect { cardList ->
                if (cardList.isNotEmpty()) {
                    _uiState.value = DebitCardsUiState.Success(cardList)
                }
            }
        }
        refreshDebitCards()
    }

    fun refreshDebitCards() {
        viewModelScope.launch {
            _uiState.value = DebitCardsUiState.Loading

            when (val result = repository.refreshDebitCards()) {
                is CardResult.Success -> { }
                is CardResult.Error -> {
                    _uiState.value = DebitCardsUiState.Error(result.message)
                }
                is CardResult.Loading -> {
                    _uiState.value = DebitCardsUiState.Loading
                }
            }
        }
    }
}