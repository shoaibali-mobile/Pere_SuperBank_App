package com.shoaib.cards.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.cards.model.CardType

/**
 * Card Type Icon Component
 * Displays the appropriate icon for each card type
 */
@Composable
fun CardTypeIcon(cardType: CardType) {
    when (cardType) {
        CardType.CreditCards -> {
            CreditCardIcon(
                cardColor = Color(0xFF3B82F6),
                chipColor = Color(0xFF1E40AF)
            )
        }
        CardType.DebitCards -> {
            CreditCardIcon(
                cardColor = Color(0xFF22C55E),
                chipColor = Color(0xFF15803D)
            )
        }
        CardType.VirtualCards -> {
            VirtualCardIcon()
        }
        CardType.CardSettings -> {
            SettingsIcon()
        }
    }
}

@Composable
private fun CreditCardIcon(
    cardColor: Color,
    chipColor: Color
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(cardColor)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp, 16.dp)
                .align(Alignment.CenterStart)
                .padding(start = 4.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(chipColor)
        )
    }
}

@Composable
private fun VirtualCardIcon() {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = Color(0xFFA855F7),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .size(24.dp, 16.dp)
                .align(Alignment.CenterStart)
                .padding(start = 4.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(
                    width = 1.dp,
                    color = Color(0xFFA855F7),
                    shape = RoundedCornerShape(4.dp)
                )
        )
        Text(
            text = "123",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFA855F7),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun SettingsIcon() {
    Icon(
        imageVector = Icons.Default.Settings,
        contentDescription = "Settings",
        tint = Color(0xFFF59E0B),
        modifier = Modifier.size(48.dp)
    )
}
