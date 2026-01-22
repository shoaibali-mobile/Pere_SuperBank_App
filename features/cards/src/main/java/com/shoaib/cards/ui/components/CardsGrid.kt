package com.shoaib.cards.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shoaib.cards.model.CardType

/**
 * Cards Grid Component
 * Displays a 2x2 grid of card type options
 */
@Composable
fun CardsGrid(
    onCardTypeClick: (CardType) -> Unit
) {
    val cardTypes = listOf(
        CardType.CreditCards,
        CardType.DebitCards,
        CardType.VirtualCards,
        CardType.CardSettings
    )
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(cardTypes) { cardType ->
            CardTypeItem(
                cardType = cardType,
                onClick = { onCardTypeClick(cardType) }
            )
        }
    }
}
