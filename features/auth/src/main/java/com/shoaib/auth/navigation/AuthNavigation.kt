package com.shoaib.auth.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shoaib.auth.ui.login.LoginScreen
import com.shoaib.auth.ui.login.LoginViewModel
import com.shoaib.auth.ui.register.RegisterScreen
import com.shoaib.auth.ui.pin.PinScreen
import com.shoaib.auth.ui.pin.PinViewModel
import com.shoaib.navigation.AuthRoute

/**
 * Auth Navigation Graph
 * 
 * Encapsulates the internal flow of the Auth feature.
 */
fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    onNavigateToHome: () -> Unit
) {
    composable<AuthRoute.Login> {
        val viewModel: LoginViewModel = hiltViewModel()
        LoginScreen(
            viewModel = viewModel,
            onLoginSuccess = {
                navController.navigateToPin()
            },
            onRegisterClick = {
                navController.navigateToRegister()
            }
        )
    }

    composable<AuthRoute.Register> {
        RegisterScreen(
            onRegisterComplete = {
                navController.navigateToLogin()
            }
        )
    }
    
    composable<AuthRoute.Pin> {
        val viewModel: PinViewModel = hiltViewModel()
        PinScreen(
            viewModel = viewModel,
            onPinSuccess = {
                onNavigateToHome()
            }
        )
    }
}

fun NavHostController.navigateToLogin() {
    navigate(AuthRoute.Login) {
        popUpTo(AuthRoute.Login) { inclusive = true }
    }
}

fun NavHostController.navigateToPin() {
    navigate(AuthRoute.Pin) {
        popUpTo(AuthRoute.Login) { inclusive = false }
    }
}

fun NavHostController.navigateToRegister() {
    navigate(AuthRoute.Register) {
        popUpTo(AuthRoute.Login) { inclusive = false }
    }
}
