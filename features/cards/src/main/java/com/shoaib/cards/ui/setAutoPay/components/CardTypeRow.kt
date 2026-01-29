package com.shoaib.cards.ui.setAutoPay.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.shoaib.cards.utils.CardUtils
import com.shoaib.design.theme.SuperAppDesign

@Composable
fun CardTypeRow(
    cardTypeDisplay: String,
    maskedNumber: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SuperAppDesign.GlassWhite)
            .border(1.dp, SuperAppDesign.GlassBorder, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = CardUtils.getCardLogo(cardTypeDisplay)),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = cardTypeDisplay,
                    color = SuperAppDesign.TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = maskedNumber,
                    color = SuperAppDesign.TextSecondary,
                    fontSize = 14.sp
                )
            }
        }
    }
}
