package com.shoaib.auth.ui.pin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PinScreen(
    viewModel: PinViewModel,
    onPinSuccess: () -> Unit
) {
    val uiState by viewModel.uistate.collectAsStateWithLifecycle()

    // Handle Success Navigation
    LaunchedEffect(uiState) {
        if (uiState is PinUiState.Success) {
            onPinSuccess()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val state = uiState) {
            is PinUiState.Loading -> CircularProgressIndicator()
            is PinUiState.Content -> {
                Text(
                    text = if (state.mode == PinMode.Setup) "Create a PIN" else "Enter PIN",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(32.dp))

                // The Dots
                PinIndicator(length = 4, filledCount = state.enteredPin.length)

                if (state.error != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = state.error, color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(48.dp))

                // The Numpad
                PinKeypad(
                    onDigitClick = { viewModel.onDigitEntered(it) },
                    onBackspaceClick = { viewModel.onBackspace() }
                )
            }
            is PinUiState.Success -> {
                // Success handled by LaunchedEffect above
            }
        }
    }
}

@Composable
fun PinIndicator(length: Int, filledCount: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        repeat(length) { index ->
            val isFilled = index < filledCount
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (isFilled) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
        }
    }
}

@Composable
fun PinKeypad(onDigitClick: (String) -> Unit, onBackspaceClick: () -> Unit) {
    val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", "DEL")

    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        keys.chunked(3).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { key ->
                    if (key.isEmpty()) {
                        Spacer(modifier = Modifier.size(64.dp))
                    } else if (key == "DEL") {
                        TextButton(onClick = onBackspaceClick, modifier = Modifier.size(64.dp)) {
                            Text("⌫", fontSize = 24.sp)
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable { onDigitClick(key) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = key, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}