package com.shoaib.cards.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shoaib.cards.model.CardType
import com.shoaib.cards.ui.components.CardsGrid
import com.shoaib.cards.ui.components.NavigationHeader
import com.shoaib.cards.ui.components.SectionHeader
import com.shoaib.cards.viewmodel.CreditCardsUiState
import com.shoaib.cards.viewmodel.CreditCardsViewModel
import com.shoaib.design.components.GlassScaffold
import com.shoaib.design.components.SuperLoading

/**
 * Credit Cards Screen - Manage and view payment cards
 */
@Composable
fun CreditCardsScreen(
    modifier: Modifier = Modifier,
    viewModel: CreditCardsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onCardTypeClick: (CardType) -> Unit = {},
    onCardClick: (String) -> Unit = {}, // Navigate to card details by ID
) {
    val uiState by viewModel.uiState.collectAsState()

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

            if (uiState is CreditCardsUiState.Loading) {
                SuperLoading(
                    message = "Fetching cards...",
                    modifier = Modifier.weight(1f)
                )
            } else {
                SectionHeader()

                Spacer(Modifier.height(24.dp))

                // Show API Data State
                when (val state = uiState) {
                    is CreditCardsUiState.Error -> Text(
                        "Error: ${state.message}",
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                    is CreditCardsUiState.Success -> {
                        Text(
                            "Found ${state.cards.size} Credit Cards",
                            color = androidx.compose.ui.graphics.Color.Green
                        )
                        state.cards.forEach { card ->
                            Text(
                                text = "• ${card.cardNumber} (${card.cardHolderName})",
                                color = androidx.compose.ui.graphics.Color.White,
                                modifier = Modifier.clickable {
                                    onCardClick(card.id) // Navigate to details with card ID
                                }
                            )
                        }
                    }
                    else -> {}
                }

                Spacer(Modifier.height(24.dp))

                CardsGrid(
                    onCardTypeClick = { type ->
                        // Smart Navigation: If we have the card data loaded, open the actual card
                        val currentState = uiState
                        if (currentState is CreditCardsUiState.Success && type == CardType.CreditCards) {
                            val firstCard = currentState.cards.firstOrNull()
                            if (firstCard != null) {
                                onCardClick(firstCard.id) // Use the REAL ID
                            } else {
                                onCardTypeClick(type) // Fallback if list empty
                            }
                        } else {
                            onCardTypeClick(type) // Fallback for other types
                        }
                    }
                )
            }
        }
    }
}

// ========== PREVIEWS ==========

@Preview(
    name = "Credit Cards Screen - Full Preview",
    showBackground = false,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
private fun CreditCardsScreenPreview() {
    CreditCardsScreen()
}
