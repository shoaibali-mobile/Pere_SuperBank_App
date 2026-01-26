package com.shoaib.cards.ui.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.cards.R
import com.shoaib.cards.model.CardType
import com.shoaib.design.theme.SuperAppDesign

/**
 * Premium Payment Card Back Side
 */
@Composable
fun PaymentCardBack(
    modifier: Modifier = Modifier,
    cvv: String = "123",
    cardholderName: String = "Bruce Wayne",
    cardType: CardType = CardType.CreditCards
) {
    val gradientColors = when (cardType) {
        CardType.DebitCards -> listOf(Color(0xFF8E2E2E), Color(0xFF4A0E0E)) // Maroon/Red for Debit
        CardType.VirtualCards -> listOf(Color(0xFF323232), Color(0xFF000000)) // Blackish for Virtual
        else -> listOf(Color(0xFF2A5298), Color(0xFF1E3C72)) // Blue for Credit
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
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
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Magnetic Stripe
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color(0xFF2D1F16)) // Dark brown/black stripe
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Signature Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .background(Color.White.copy(alpha = 0.9f)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = cardholderName,
                        modifier = Modifier.padding(start = 12.dp),
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.rockybilly)),
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(36.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cvv,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // RuPay Logo
                Image(
                    painter = painterResource(id = R.drawable.rupay),
                    contentDescription = "RuPay Logo",
                    modifier = Modifier.size(48.dp)
                )
            }

            // Fine Print and Bank Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 8.dp, bottom = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Bank at Rawareh Raktuby Bank",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                Text(
                    text = "00-974063673737",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Text(
                    text = "www.rupay.com",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "This card is property of the bank and must be returned upon request.\nUse subject to terms and conditions.",
                    fontSize = 8.sp,
                    lineHeight = 10.sp,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF0F0C29)
@Composable
private fun PaymentCardBackPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
          PaymentCardBack(
            cvv = "123",
            cardholderName = "JOHN DOE"
        )
    }
}