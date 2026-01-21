package com.shoaib.design.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.shoaib.design.theme.appBackground

/**
 * A standard screen wrapper for the Super App
 * Applies the gradient background and provides a transparent Scaffold
 */
@Composable
fun GlassScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .appBackground()
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            content = content
        )
    }
}
