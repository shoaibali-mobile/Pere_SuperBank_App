package com.shoaib.cards.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.shoaib.cards.model.CardType
import com.shoaib.cards.ui.CreditCardsScreen
import com.shoaib.cards.ui.details.CardDetailsScreen
import com.shoaib.navigation.CardsRoute
import com.shoaib.navigation.CardDetailsRoute

import com.shoaib.cards.ui.managelimits.ManageLimitsScreen
import com.shoaib.cards.ui.resetCardPin.SetResetPinScreen
import com.shoaib.cards.ui.requestAddOnCard.RequestAddOnCardScreen
import com.shoaib.cards.ui.setAutoPay.SetAutopayScreen
import com.shoaib.navigation.ManageLimitsRoute
import com.shoaib.navigation.RequestAddOnCardRoute
import com.shoaib.navigation.SetAutopayRoute
import com.shoaib.navigation.SetResetPinRoute

fun NavGraphBuilder.cardsGraph(
    onBack: () -> Unit,
    onOpenCardDetails: (String) -> Unit,
    onNavigateToManageLimits: (String) -> Unit,
    onNavigateToSetResetPin: (String) -> Unit,
    onNavigateToSetAutopay: (String) -> Unit,
    onNavigateToRequestAddOnCard: (String) -> Unit
) {
    composable<CardsRoute> {
        CreditCardsScreen(
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
            onBackClick = onBack,
            onManageCardClick = {
                onNavigateToManageLimits(route.cardId)
            },
            onSetResetPinClick = {
                onNavigateToSetResetPin(route.cardId)
            },
            onSetAutopayClick = {
                onNavigateToSetAutopay(route.cardId)
            },
            onRequestAddOnCardClick = {
                onNavigateToRequestAddOnCard(route.cardId)
            }
        )
    }
    
    composable<ManageLimitsRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<ManageLimitsRoute>()
        ManageLimitsScreen(
            onBackClick = onBack
        )
    }

    composable<SetResetPinRoute> {
        SetResetPinScreen(onBackClick = onBack)
    }

    composable<SetAutopayRoute> {
        SetAutopayScreen(onBackClick = onBack)
    }

    composable<RequestAddOnCardRoute> {
        RequestAddOnCardScreen(onBackClick = onBack)
    }
}
