package com.shoaib.design.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


private val SuccessGreen = Color(0xFF4CAF50)
private val ErrorRed = Color(0xFFD32F2F)
private val ContentWhite = Color.White


@Composable
fun ThemedSnackBarHost(
    hostState: SnackbarHostState,
    isError: Boolean,
    modifier: Modifier = Modifier
){

    SnackbarHost(
        hostState = hostState,
        modifier = modifier.fillMaxWidth(),
        snackbar = {data: SnackbarData ->
            Snackbar(
                snackbarData = data,
                containerColor = if(isError) ErrorRed else SuccessGreen,
                contentColor = ContentWhite
            )
        }
    )
}