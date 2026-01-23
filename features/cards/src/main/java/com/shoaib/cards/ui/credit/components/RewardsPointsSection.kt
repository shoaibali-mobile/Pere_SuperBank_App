package com.shoaib.cards.ui.credit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.design.theme.SuperAppDesign

/**
 * Rewards Points Section with Glassmorphism Style
 */
@Composable
fun RewardsPointsSection(
    modifier: Modifier = Modifier,
    points: Int = 1250,
    onRedeemClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Star Icon and Label
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color(0xFFFF9966), // Orange
                        modifier = Modifier.size(24.dp)
                    )
                    
                    Spacer(Modifier.width(8.dp))
                    
                    Text(
                        text = "Rewards Points",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SuperAppDesign.TextPrimary
                    )
                }
                
                Spacer(Modifier.height(8.dp))
                
                Text(
                    text = "$points",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = SuperAppDesign.TextPrimary
                )
                
                Text(
                    text = "Available Points",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = SuperAppDesign.TextSecondary
                )
            }
            
            // Right: Redeem Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFFF9966), // Orange
                                Color(0xFFFF5E62)  // Gold/Red
                            )
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Redeem,
                        contentDescription = "Gift",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    
                    Spacer(Modifier.width(6.dp))
                    
                    Text(
                        text = "Redeem",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
