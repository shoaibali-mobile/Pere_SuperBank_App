package com.shoaib.cards.ui.resetCardPin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.cards.R
import com.shoaib.design.theme.SuperAppDesign

@Composable
fun CardNumberRow(
    cardNumber: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SuperAppDesign.GlassWhite)
            .border(1.dp, SuperAppDesign.GlassBorder, RoundedCornerShape(16.dp))
            .clickable(onClick = onCardClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Card Number",
                color = SuperAppDesign.TextSecondary,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.chip),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = cardNumber,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Select card",
            tint = SuperAppDesign.TextSecondary,
            modifier = Modifier.size(24.dp)
        )
    }
}
