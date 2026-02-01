package com.shoaib.cards.ui.credit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shoaib.cards.model.CardType
import com.shoaib.cards.ui.components.CardsListContent
import com.shoaib.cards.ui.components.NavigationHeader
import com.shoaib.cards.viewmodel.CreditCardsUiState
import com.shoaib.cards.viewmodel.CreditCardsViewModel
import com.shoaib.design.components.GlassScaffold

/**
 * Credit Cards Screen - Manage and view payment cards
 */
@Composable
fun CreditCardsScreen(
    modifier: Modifier = Modifier,
    viewModel: CreditCardsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onCardTypeClick: (CardType) -> Unit = {},
    onCardClick: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CreditCardsContent(
        uiState = uiState,
        modifier = modifier,
        onBackClick = onBackClick,
        onCardTypeClick = onCardTypeClick,
        onCardClick = onCardClick
    )
}

@Composable
fun CreditCardsContent(
    uiState: CreditCardsUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onCardTypeClick: (CardType) -> Unit = {},
    onCardClick: (String) -> Unit = {},
) {
    GlassScaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            NavigationHeader(onBackClick = onBackClick)
            Spacer(Modifier.height(24.dp))

            val cards = (uiState as? CreditCardsUiState.Success)?.cards.orEmpty()
            val errorMessage = (uiState as? CreditCardsUiState.Error)?.message
            val cardLines = cards.map { "• ${it.cardNumber} (${it.cardHolderName})" }

            CardsListContent(
                title = if (cards.isNotEmpty()) "Found ${cards.size} Credit Cards" else "Credit Cards",
                headerTitle = "Credit Cards",
                isLoading = uiState is CreditCardsUiState.Loading,
                errorMessage = errorMessage,
                cardsTextLines = cardLines,
                onCardClick = { index -> cards.getOrNull(index)?.let { onCardClick(it.id) } },
                loadingMessage = "Fetching credit cards..."
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}



@Preview(
    name = "Credit Cards Screen - Full Preview",
    showBackground = false,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
private fun CreditCardsScreenPreview() {
    CreditCardsContent(uiState = CreditCardsUiState.Loading)
}
