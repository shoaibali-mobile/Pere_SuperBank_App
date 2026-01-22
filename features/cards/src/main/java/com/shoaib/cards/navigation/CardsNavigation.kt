package com.shoaib.cards.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shoaib.cards.ui.CardsScreen
import com.shoaib.navigation.CardsRoute

fun NavGraphBuilder.cardsGraph(
    onBack: () -> Unit
) {
    composable<CardsRoute> {
        CardsScreen(onBackClick = onBack)
    }
}
