package com.shoaib.cards.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shoaib.cards.model.CardType
import com.shoaib.cards.ui.CardsScreen
import com.shoaib.cards.ui.credit.CreditCardScreen
import com.shoaib.navigation.CardsRoute
import com.shoaib.navigation.CreditCardRoute

fun NavGraphBuilder.cardsGraph(
    onBack: () -> Unit,
    onOpenCreditCard: () -> Unit
) {
    composable<CardsRoute> {
        CardsScreen(
            onBackClick = onBack,
            onCardTypeClick = { type ->
                if (type == CardType.CreditCards) onOpenCreditCard()
            }
        )
    }

    composable<CreditCardRoute> {
        CreditCardScreen(onBackClick = onBack)
    }
}
