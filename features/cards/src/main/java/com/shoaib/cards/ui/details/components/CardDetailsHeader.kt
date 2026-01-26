package com.shoaib.cards.ui.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.design.theme.SuperAppDesign

/**
 * Card Details Header Section
 * Displays title, subtitle, and card count badge
 */
@Composable
fun CardDetailsHeader(
    modifier: Modifier = Modifier,
    title: String = "Credit Cards",
    subtitle: String = "Your Credit Cards",
    cardCount: Int = 1
) {
    Column(modifier = modifier) {
        // Main Title
        Text(
            text = title,
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold,
            color = SuperAppDesign.TextPrimary,
            letterSpacing = (-1).sp
        )
        
        Spacer(Modifier.height(8.dp))
        
        // Subtitle and Badge Row
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text(
                text = subtitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = SuperAppDesign.TextPrimary
            )
            
            Spacer(Modifier.width(12.dp))
            
            // Card Count Badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.15f))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "$cardCount card",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = SuperAppDesign.TextSecondary
                )
            }
        }
    }
}
