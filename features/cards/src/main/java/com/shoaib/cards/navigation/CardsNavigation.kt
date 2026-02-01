package com.shoaib.cards.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.shoaib.cards.model.CardType
import com.shoaib.cards.ui.CardsDashboardScreen
import com.shoaib.cards.ui.credit.CreditCardsScreen
import com.shoaib.cards.ui.debit.DebitCardDetailsScreen
import com.shoaib.cards.ui.debit.DebitCardsScreen
import com.shoaib.cards.ui.details.CardDetailsScreen
import com.shoaib.navigation.CardsRoute
import com.shoaib.navigation.CardDetailsRoute
import com.shoaib.navigation.CreditCardsRoute
import com.shoaib.navigation.DebitCardDetailsRoute
import com.shoaib.navigation.DebitCardsRoute

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
    onOpenDebitCardDetails: (String) -> Unit,
    onNavigateToDebitCards: () -> Unit,
    onNavigateToCreditCards: () -> Unit,
    onNavigateToManageLimits: (String) -> Unit,
    onNavigateToSetResetPin: (String, Boolean) -> Unit,
    onNavigateToSetAutopay: (String) -> Unit,
    onNavigateToRequestAddOnCard: (String) -> Unit
) {
    composable<CardsRoute> {
        CardsDashboardScreen(
            onBackClick = onBack,
            onCardTypeClick = { type ->
                when (type) {
                    CardType.CreditCards -> onOpenCardDetails("first_card")
                    CardType.DebitCards -> onOpenDebitCardDetails("first_card")
                    CardType.VirtualCards -> onOpenCardDetails("VIRTUAL")
                    CardType.CardSettings -> { /* Handle card settings */ }
                }
            }
        )
    }

    composable<CreditCardsRoute> {
        CreditCardsScreen(
            onBackClick = onBack,
            onCardTypeClick = { type ->
                if (type == CardType.CreditCards) onOpenCardDetails("CREDIT")
                if (type == CardType.DebitCards) onNavigateToDebitCards()
                if (type == CardType.VirtualCards) onOpenCardDetails("VIRTUAL")
            },
            onCardClick = { cardId ->
                // Navigate to card details screen with the card ID
                onOpenCardDetails(cardId)
            }
        )
    }

    composable<DebitCardsRoute> {
        DebitCardsScreen(
            onBackClick = onBack,
            onCardTypeClick = { type ->
                if (type == CardType.CreditCards) onOpenCardDetails("CREDIT")
                if (type == CardType.DebitCards) onNavigateToDebitCards()
                if (type == CardType.VirtualCards) onOpenCardDetails("VIRTUAL")
            },
            onCardClick = { cardId ->
                onOpenDebitCardDetails(cardId)
            }
        )
    }

    composable<DebitCardDetailsRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<DebitCardDetailsRoute>()
        DebitCardDetailsScreen(
            cardId = route.cardId,
            onBackClick = onBack,
            onManageCardClick = { cardId -> onNavigateToManageLimits(cardId) },
            onSetResetPinClick = { cardId -> onNavigateToSetResetPin(cardId,true) },
            onSetAutopayClick = { cardId -> onNavigateToSetAutopay(cardId) },
            onRequestAddOnCardClick = { cardId -> onNavigateToRequestAddOnCard(cardId) }
        )
    }

    composable<CardDetailsRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<CardDetailsRoute>()
        CardDetailsScreen(
            cardId = route.cardId,
            onBackClick = onBack,
            onManageCardClick = { cardId -> onNavigateToManageLimits(cardId) },
            onSetResetPinClick = { cardId -> onNavigateToSetResetPin(cardId,false) },
            onSetAutopayClick = { cardId -> onNavigateToSetAutopay(cardId) },
            onRequestAddOnCardClick = { cardId -> onNavigateToRequestAddOnCard(cardId) }
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
