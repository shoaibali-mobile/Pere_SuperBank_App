package com.shoaib.cards.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shoaib.design.components.SuperLoading

@Composable
fun CardsListContent(
    title: String,
    headerTitle: String = "Your Cards",
    isLoading: Boolean,
    errorMessage: String?,
    cardsTextLines: List<String>,
    onCardClick: (Int) -> Unit,
    loadingMessage: String = "Fetching cards..."
) {
    if (isLoading) {
        SuperLoading(message = loadingMessage)
        return
    }

    SectionHeader(title = headerTitle)
    Spacer(Modifier.height(24.dp))

    when {
        errorMessage != null -> {
            Text("Error: $errorMessage", color = Color.Red)
        }
        cardsTextLines.isNotEmpty() -> {
            Text(title, color = Color.Green)
            cardsTextLines.forEachIndexed { index, line ->
                Text(
                    text = line,
                    color = Color.White,
                    modifier = Modifier.clickable { onCardClick(index) }
                )
            }
        }
        else -> {
            Text("No cards found", color = Color.White)
        }
    }
}
