package com.shoaib.design.theme

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Shared Design Constants for the "Super App" Look
 */
object SuperAppDesign {
    // 1. The Core Gradient
    val GradientStart = Color(0xFF0F0C29)   // Deep midnight purple
    val GradientMiddle = Color(0xFF302B63)  // Purple-blue
    val GradientEnd = Color(0xFF24243E)     // Dark navy
    
    val backgroundBrush = Brush.linearGradient(
        colors = listOf(GradientStart, GradientMiddle, GradientEnd),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f) // 45° angle
    )

    // 2. Standard Glass Opacities
    val GlassWhite = Color.White.copy(alpha = 0.12f)
    val GlassBorder = Color.White.copy(alpha = 0.2f)
    
    // 3. Typography Opacities
    val TextPrimary = Color.White
    val TextSecondary = Color.White.copy(alpha = 0.7f)
    val TextHint = Color.White.copy(alpha = 0.5f)
}

/**
 * Modifier extension for easy reuse of the app background
 */
fun Modifier.appBackground(): Modifier = this.background(brush = SuperAppDesign.backgroundBrush)
