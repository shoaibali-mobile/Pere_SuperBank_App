package com.shoaib.cards.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shoaib.cards.model.CardType
import com.shoaib.cards.ui.components.CardsGrid
import com.shoaib.cards.ui.components.NavigationHeader
import com.shoaib.cards.ui.components.SectionHeader
import com.shoaib.design.components.GlassScaffold

/**
 * Cards Screen - Manage and view payment cards
 */
@Composable
fun CardsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onCardTypeClick: (CardType) -> Unit = {}
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
            
            SectionHeader()
            
            Spacer(Modifier.height(24.dp))
            
            CardsGrid(
                onCardTypeClick = onCardTypeClick
            )
        }
    }
}

// ========== PREVIEWS ==========

@Preview(
    name = "Cards Screen - Full Preview",
    showBackground = false,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
private fun CardsScreenPreview() {
    CardsScreen()
}
