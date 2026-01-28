package com.shoaib.cards.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.shoaib.cards.model.CardType
import com.shoaib.cards.ui.CardsScreen
import com.shoaib.cards.ui.details.CardDetailsScreen
import com.shoaib.navigation.CardsRoute
import com.shoaib.navigation.CardDetailsRoute

fun NavGraphBuilder.cardsGraph(
    onBack: () -> Unit,
    onOpenCardDetails: (String) -> Unit
) {
    composable<CardsRoute> {
        CardsScreen(
            onBackClick = onBack,
            onCardTypeClick = { type ->
                if (type == CardType.CreditCards) onOpenCardDetails("CREDIT")
                if (type == CardType.DebitCards) onOpenCardDetails("DEBIT")
                if (type == CardType.VirtualCards) onOpenCardDetails("VIRTUAL")
            },
            onCardClick = { cardId ->
                // Navigate to card details screen with the card ID
                onOpenCardDetails(cardId)
            }
        )
    }

    composable<CardDetailsRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<CardDetailsRoute>()
        CardDetailsScreen(
            cardId = route.cardId,
            onBackClick = onBack
        )
    }
}
