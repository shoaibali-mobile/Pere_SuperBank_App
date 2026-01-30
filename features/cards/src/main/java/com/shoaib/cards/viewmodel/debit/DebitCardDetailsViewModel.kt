package com.shoaib.cards.viewmodel.debit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.cards.data.debit.repository.DebitCardsRepository
import com.shoaib.cards.viewmodel.CardDetailsUiModel
import com.shoaib.cards.viewmodel.CardDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebitCardDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: DebitCardsRepository
) : ViewModel() {

    // Get cardId from SavedStateHandle
    private val cardId: String = checkNotNull(savedStateHandle["cardId"])

    init {
        viewModelScope.launch {
            repository.refreshDebitCards()
        }
    }

    val uiState: StateFlow<CardDetailsUiState> = repository.getDebitCardsStream()
        .map { cards ->
            val uiModels = cards.map { card ->
                CardDetailsUiModel(
                    id = card.id,
                    cardNumber = card.cardNumber,
                    cardHolderName = card.cardholderName,
                    cardType = card.cardType,
                    expiryMonth = card.expiryMonth,
                    expiryYear = card.expiryYear,
                    cvv = card.cvv,
                    rewardsPoints = 0, // Debit cards usually don't have this in this app context
                    isCredit = false,
                    bankName = card.bankName,
                    accountNumber = card.accountNumber
                )
            }

            val index = if (cardId == "first_card") {
                if (uiModels.isNotEmpty()) 0 else -1
            } else {
                uiModels.indexOfFirst { it.id == cardId }
            }

            if (index != -1) {
                CardDetailsUiState.Success(uiModels, index)
            } else {
                CardDetailsUiState.Error("Debit Card not found")
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CardDetailsUiState.Loading
        )
}
