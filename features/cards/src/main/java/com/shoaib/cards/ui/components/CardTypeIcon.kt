package com.shoaib.cards.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.WifiTethering
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shoaib.cards.R
import com.shoaib.cards.model.CardType

/**
 * Card Type Icon Component
 * Displays the appropriate icon for each card type
 */
@Composable
fun CardTypeIcon(cardType: CardType) {
    when (cardType) {
        CardType.CreditCards -> {
            Image(
                painter = painterResource(id = R.drawable.credit_card),
                contentDescription = "Card Settings",
                modifier = Modifier.size(48.dp)
            )
        }
        CardType.DebitCards -> {
            Image(
                painter = painterResource(id = R.drawable.debit_card),
                contentDescription = "Card Settings",
                modifier = Modifier.size(48.dp)
            )
        }
        CardType.VirtualCards -> {
            Image(
                painter = painterResource(id = R.drawable.virtual_card),
                contentDescription = "Card Settings",
                modifier = Modifier.size(48.dp)
            )
        }
        CardType.CardSettings -> {
            Image(
                painter = painterResource(id = R.drawable.payments_settings),
                contentDescription = "Card Settings",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
