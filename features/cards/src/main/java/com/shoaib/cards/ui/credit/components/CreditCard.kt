package com.shoaib.cards.ui.credit.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.cards.R
import com.shoaib.design.theme.SuperAppDesign

/**
 * Premium Credit Card Component with Metallic Finish
 */
@Composable
fun CreditCard(
    modifier: Modifier = Modifier,
    cardNumber: String = "9012 3456 7890 1234",
    expiryDate: String = "12/26",
    cardholderName: String = "JOHN DOE",
    onCardNumberToggle: () -> Unit = {}
) {
    var isCardNumberVisible by remember { mutableStateOf(false) }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2A5298), // Lighter steel blue (center)
                        Color(0xFF1E3C72)  // Deep metallic blue (edges)
                    ),
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
            // Top Row: Chip and RuPay Logo
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // EMV Chip
                Image(
                    painter = painterResource(id = R.drawable.chip),
                    contentDescription = "EMV Chip",
                    modifier = Modifier.size(48.dp)
                )
                
                Spacer(Modifier.weight(1f))
                
                // RuPay Logo with holographic glow
                Image(
                    painter = painterResource(id = R.drawable.rupay),
                    contentDescription = "RuPay Logo",
                    modifier = Modifier.size(48.dp)
                )
            }
            
            Spacer(Modifier.height(24.dp))
            
            // Card Number with Eye Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val maskedNumber = cardNumber.replaceRange(0, cardNumber.length - 4, "**** **** **** ")
                Text(
                    text = if (isCardNumberVisible) cardNumber else maskedNumber,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
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
            
            // Bottom Row: Expiry and Name
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
