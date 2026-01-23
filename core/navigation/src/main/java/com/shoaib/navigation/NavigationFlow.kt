package com.shoaib.navigation

import androidx.navigation.NavHostController


fun NavHostController.navigateToHome() {
    navigate(HomeRoute) {
        // Clear back stack - user can't go back to auth screens
        popUpTo(graph.startDestinationId) { inclusive = true }
    }
}

fun NavHostController.navigateToProfile() {
    navigate(ProfileRoute) {
        // Keep back stack - user can go back to previous screen
    }
}
