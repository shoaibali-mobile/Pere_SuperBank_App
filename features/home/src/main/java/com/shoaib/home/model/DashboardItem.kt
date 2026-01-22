package com.shoaib.home.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class DashboardItem(
    val title: String,
    val icon: ImageVector,
    val tint: Color,
    val onClick: () -> Unit = {}
)
