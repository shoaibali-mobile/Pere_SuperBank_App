package com.shoaib.cards.ui.debit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shoaib.cards.model.CardType
import com.shoaib.cards.model.debit.DebitCardsUiState
 import com.shoaib.cards.ui.components.CardsListContent
import com.shoaib.cards.ui.components.NavigationHeader
import com.shoaib.cards.viewmodel.debit.DebitCardsViewModel
import com.shoaib.design.components.GlassScaffold

import com.shoaib.cards.model.debit.DebitCardDto

/**
 * Debit Cards Screen - Manage and view payment cards
 */
@Composable
fun DebitCardsScreen(
    modifier: Modifier = Modifier,
    viewModel: DebitCardsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onCardTypeClick: (CardType) -> Unit = {},
    onCardClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DebitCardsContent(
        uiState = uiState,
        modifier = modifier,
        onBackClick = onBackClick,
        onCardTypeClick = onCardTypeClick,
        onCardClick = onCardClick
    )
}

@Composable
fun DebitCardsContent(
    uiState: DebitCardsUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onCardTypeClick: (CardType) -> Unit = {},
    onCardClick: (String) -> Unit = {}
) {
    GlassScaffold(
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            NavigationHeader(
                onBackClick = onBackClick
            )

            Spacer(Modifier.height(24.dp))

            val cards = (uiState as? DebitCardsUiState.Success)?.cards.orEmpty()
            val errorMessage = (uiState as? DebitCardsUiState.Error)?.message
            val cardLines = cards.map { card ->
                "• ${card.cardNumber} (${card.cardholderName})"
            }

            CardsListContent(
                title = if (cards.isNotEmpty()) {
                    "Found ${cards.size} Debit Cards"
                } else {
                    "Debit Cards"
                },
                headerTitle = "Debit Cards",
                isLoading = uiState is DebitCardsUiState.Loading,
                errorMessage = errorMessage,
                cardsTextLines = cardLines,
                onCardClick = { index ->
                    cards.getOrNull(index)?.let { onCardClick(it.id) }
                },
                loadingMessage = "Fetching debit cards..."
            )

            Spacer(Modifier.height(24.dp))

        }
    }
}

@Preview(
    name = "Debit Cards Screen - Full Preview",
    showBackground = false,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
private fun DebitCardsScreenPreview() {
    DebitCardsContent(
        uiState = DebitCardsUiState.Success(
            cards = listOf(
                DebitCardDto(
                    id = "1",
                    accountNumber = "50123456789012",
                    bankName = "HDFC Bank",
                    cardNumber = "6529 **** 7890",
                    cardType = "Rupay",
                    cardholderName = "Bruce Wayne",
                    cvv = "***",
                    expiryMonth = 10,
                    expiryYear = 2028
                )
            )
        )
    )
}
