package com.shoaib.cards.ui.details.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.cards.R
import com.shoaib.cards.model.CardFace
import com.shoaib.cards.model.CardType
import com.shoaib.design.theme.SuperAppDesign

/**
 * Premium Payment Card Component with 3D Flip Animation
 */
@Composable
fun PaymentCard(
    cardFace: CardFace,
    modifier: Modifier = Modifier,
    cardType: CardType = CardType.CreditCards,
    cardNumber: String = "9012 3456 7890 1234",
    expiryDate: String = "12/26",
    cardholderName: String = "Bruce Wayne",
    cvv: String = "123"
) {
    val rotation by animateFloatAsState(
        targetValue = if (cardFace == CardFace.Front) 0f else 180f,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
    ) {
        if (rotation <= 90f) {
            PaymentCardFront(
                cardNumber = cardNumber,
                expiryDate = expiryDate,
                cardholderName = cardholderName,
                cardType = cardType
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180f
                    }
            ) {
                PaymentCardBack(
                    cvv = cvv,
                    cardholderName = cardholderName,
                    cardType = cardType,
                )
            }
        }
    }
}

@Composable
private fun PaymentCardFront(
    cardNumber: String,
    expiryDate: String,
    cardholderName: String,
    cardType: CardType
) {
    var isCardNumberVisible by remember { mutableStateOf(false) }
    
    val gradientColors = when (cardType) {
        CardType.DebitCards -> listOf(Color(0xFF8E2E2E), Color(0xFF4A0E0E)) // Maroon/Red for Debit
        CardType.VirtualCards -> listOf(Color(0xFF323232), Color(0xFF000000)) // Blackish for Virtual
        else -> listOf(Color(0xFF2A5298), Color(0xFF1E3C72)) // Blue for Credit
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.radialGradient(
                    colors = gradientColors,
                    center = Offset(0.3f, 0.3f)
                )
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chip),
                    contentDescription = "EMV Chip",
                    modifier = Modifier.size(56.dp)
                )
                
                Spacer(Modifier.weight(1f))
                
                Image(
                    painter = painterResource(id = R.drawable.rupay),
                    contentDescription = "RuPay Logo",
                    modifier = Modifier.size(48.dp)
                )
            }
            
            Spacer(Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val maskedNumber = cardNumber.replaceRange(0, cardNumber.length - 4, "**** **** **** ")
                Text(
                    text = if (isCardNumberVisible) cardNumber else maskedNumber,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = SuperAppDesign.TextPrimary,
                    letterSpacing = 2.sp,
                    modifier = Modifier.weight(1f)
                )
                
                Icon(
                    imageVector = if (isCardNumberVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "Toggle card number",
                    tint = SuperAppDesign.TextPrimary.copy(alpha = 0.7f),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { isCardNumberVisible = !isCardNumberVisible }
                )
            }
            
            Spacer(Modifier.height(20.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "VALID THRU",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        color = SuperAppDesign.TextSecondary
                    )
                    Text(
                        text = expiryDate,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = SuperAppDesign.TextPrimary
                    )
                }
                
                Spacer(Modifier.weight(1f))
                
                Text(
                    text = cardholderName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SuperAppDesign.TextPrimary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}
