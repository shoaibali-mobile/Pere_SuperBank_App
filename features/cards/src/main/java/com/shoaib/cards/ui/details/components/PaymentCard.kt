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

import com.shoaib.cards.ui.theme.CardGradients
import com.shoaib.cards.utils.CardUtils

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
    cvv: String = "123",
    cardNetwork: String = "RUPAY",
    cardIndex: Int = 0 // Add cardIndex parameter
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
                cardType = cardType,
                cardNetwork = cardNetwork,
                cardIndex = cardIndex
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
                    cardIndex = cardIndex,
                    cardNetwork = cardNetwork // Pass cardNetwork to back
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
    cardType: CardType,
    cardNetwork: String,
    cardIndex: Int
) {
    var isCardNumberVisible by remember { mutableStateOf(false) }
    
    // Use the Bruce Wayne persona gradients based on index
    val gradientColors = CardGradients.getGradient(cardIndex)
    
    val logoRes = CardUtils.getCardLogo(cardNetwork)
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient( // Changed to linear for sleeker look
                    colors = gradientColors,
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
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
                    painter = painterResource(id = logoRes),
                    contentDescription = "$cardNetwork Logo",
                    modifier = Modifier.size(48.dp)
                )
            }
            
            Spacer(Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Format raw number into groups of 4: "1234 5678 9012 3456"
                val formattedNumber = cardNumber.chunked(4).joinToString(" ")
                
                // Create masked version based on the formatted number: "**** **** **** 3456"
                val maskedNumber = if (formattedNumber.length >= 4) {
                    val lastFour = formattedNumber.takeLast(4)
                    val maskPrefix = formattedNumber.dropLast(4).replace(Regex("[0-9]"), "*")
                    maskPrefix + lastFour
                } else {
                    "**** **** **** ****"
                }

                Text(
                    text = if (isCardNumberVisible) formattedNumber else maskedNumber,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = SuperAppDesign.TextPrimary,
                    letterSpacing = 2.sp,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    softWrap = false
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
