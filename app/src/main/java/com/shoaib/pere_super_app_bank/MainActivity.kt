package com.shoaib.pere_super_app_bank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.shoaib.auth.navigation.authGraph
import com.shoaib.cards.navigation.cardsGraph
import com.shoaib.home.navigation.homeGraph
import com.shoaib.navigation.CardsRoute
import com.shoaib.navigation.CardDetailsRoute
import com.shoaib.navigation.CreditCardsRoute
import com.shoaib.navigation.DebitCardDetailsRoute
import com.shoaib.navigation.DebitCardsRoute
import com.shoaib.navigation.HomeRoute
import com.shoaib.pere_super_app_bank.ui.theme.PereSuperAppBankTheme
import dagger.hilt.android.AndroidEntryPoint
import com.shoaib.navigation.ManageLimitsRoute
import com.shoaib.navigation.RequestAddOnCardRoute
import com.shoaib.navigation.SetAutopayRoute
import com.shoaib.navigation.SetResetPinRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PereSuperAppBankTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    val startDestination by mainViewModel.startDestination.collectAsStateWithLifecycle()

    if (startDestination == null) {
        LoadingScreen()
        return
    }

    NavHost(
        navController = navController,
        startDestination = startDestination!!,
        modifier = Modifier.fillMaxSize()
    ) {
        // LEGO Block: Auth Feature (Login + PIN)
        authGraph(
            navController = navController,
            onNavigateToHome = {
                navController.navigate(HomeRoute) {
                    // Clear the entire auth stack when reaching Home
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
        )



        cardsGraph(
            onBack = {
                navController.navigate(HomeRoute) {
                    popUpTo(HomeRoute) { inclusive = false }
                }
            },
            onOpenCardDetails = { cardId ->
                navController.navigate(CardDetailsRoute(cardId = cardId))
            },
            onOpenDebitCardDetails = { cardId ->
                navController.navigate(DebitCardDetailsRoute(cardId = cardId))
            },
            onNavigateToDebitCards = {
                navController.navigate(DebitCardsRoute)
            },
            onNavigateToCreditCards = {
                navController.navigate(CreditCardsRoute)
            },
            onNavigateToManageLimits = { cardId ->
                navController.navigate(ManageLimitsRoute(cardId = cardId))
            },
            onNavigateToSetResetPin = { cardId ->
                navController.navigate(SetResetPinRoute(cardId = cardId))
            },
            onNavigateToSetAutopay = { cardId ->
                navController.navigate(SetAutopayRoute(cardId = cardId))
            },
            onNavigateToRequestAddOnCard = { cardId ->
                navController.navigate(RequestAddOnCardRoute(cardId = cardId))
            }
        )

        homeGraph(
            onCardsClick = { navController.navigate(CardsRoute) }
        )
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

