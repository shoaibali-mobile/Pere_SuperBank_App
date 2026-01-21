package com.shoaib.pere_super_app_bank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shoaib.auth.navigation.authGraph
import com.shoaib.home.navigation.homeGraph
import com.shoaib.home.ui.HomeScreen
import com.shoaib.navigation.HomeRoute
import com.shoaib.pere_super_app_bank.ui.theme.PereSuperAppBankTheme
import dagger.hilt.android.AndroidEntryPoint

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

        homeGraph()
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

