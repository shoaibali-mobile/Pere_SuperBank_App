package com.shoaib.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shoaib.home.ui.HomeScreen
import com.shoaib.navigation.HomeRoute



fun NavGraphBuilder.homeGraph(
    onCardsClick: () -> Unit
){
    composable<HomeRoute> {
        HomeScreen(onCardsClick = onCardsClick)
    }
}