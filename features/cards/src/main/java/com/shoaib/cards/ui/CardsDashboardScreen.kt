package com.shoaib.cards.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shoaib.cards.model.CardType
import com.shoaib.cards.ui.components.CardsGrid
import com.shoaib.cards.ui.components.NavigationHeader
import com.shoaib.cards.viewmodel.CardsDashboardViewModel
import com.shoaib.design.components.GlassScaffold
import com.shoaib.design.theme.SuperAppDesign

/**
 * Cards Dashboard Screen - Entry point for cards feature
 * Displays greeting, header and 4-grid (Credit, Debit, Virtual, Card Settings)
 */
@Composable
fun CardsDashboardScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onCardTypeClick: (CardType) -> Unit = {},
    viewModel: CardsDashboardViewModel = hiltViewModel()
) {
    val displayName by viewModel.displayName.collectAsStateWithLifecycle()

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

            Text(
                text = "Hey $displayName",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = SuperAppDesign.TextPrimary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Manage your cards and all",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = SuperAppDesign.TextSecondary
            )

            Spacer(Modifier.height(24.dp))

            CardsGrid(
                onCardTypeClick = onCardTypeClick
            )
        }
    }
}

@Preview(
    name = "Cards Dashboard Screen - Full Preview",
    showBackground = false,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
private fun CardsDashboardScreenPreview() {
    CardsDashboardScreen()
}
