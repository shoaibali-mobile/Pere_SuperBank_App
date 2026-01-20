package com.shoaib.navigation

import androidx.navigation.NavHostController

/**
 * Simple Navigation Flow Helper
 * 
 * This file provides easy-to-use navigation functions
 * that can be called from anywhere in the app.
 */

/**
 * Navigation Flow Diagram:
 * 
 * ┌─────────────────────────────────────────┐
 * │         App Start (MainActivity)          │
 * └─────────────────┬─────────────────────────┘
 *                   │
 *                   ▼
 *         ┌─────────────────┐
 *         │  MainViewModel   │
 *         │  Checks Auth    │
 *         └────────┬─────────┘
 *                  │
 *        ┌─────────┴─────────┐
 *        │                    │
 *        ▼                    ▼
 *  ┌──────────┐        ┌──────────┐
 *  │  Login   │        │   PIN    │
 *  │  Route   │        │  Route   │
 *  └────┬─────┘        └────┬─────┘
 *       │                    │
 *       │                    │
 *       ▼                    ▼
 *  ┌──────────┐        ┌──────────┐
 *  │ Login    │        │   PIN    │
 *  │ Screen   │───────▶│  Screen  │
 *  └──────────┘        └────┬─────┘
 *                            │
 *                            │ (PIN Verified)
 *                            ▼
 *                      ┌──────────┐
 *                      │  Home    │
 *                      │  Screen  │
 *                      └──────────┘
 */

/**
 * Navigation Flow:
 * 
 * 1. App Opens
 *    └─> MainViewModel checks if user is authenticated
 *        ├─> NOT authenticated → Start at LoginRoute
 *        └─> Authenticated → Start at PinRoute
 * 
 * 2. Login Screen
 *    └─> User enters credentials → LoginViewModel.login()
 *        └─> Success → Navigate to PinRoute
 * 
 * 3. PIN Screen
 *    └─> User enters PIN → PinViewModel.verifyPin()
 *        └─> Success → Navigate to HomeRoute
 * 
 * 4. Home Screen
 *    └─> User is now in the app
 */

/**
 * Helper extension functions for easy navigation
 */
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
