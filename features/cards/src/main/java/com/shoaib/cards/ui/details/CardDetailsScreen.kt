package com.shoaib.cards.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.cards.model.CardFace
import com.shoaib.cards.model.CardType
import com.shoaib.cards.ui.components.NavigationHeader
import com.shoaib.cards.ui.details.components.PaymentCard
import com.shoaib.cards.ui.details.components.CardDetailsHeader
import com.shoaib.cards.ui.details.components.ManageCardBottomSheet
import com.shoaib.cards.ui.details.components.ManageCardButton
import com.shoaib.cards.ui.details.components.RewardsPointsSection
import com.shoaib.design.components.GlassScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailsScreen(
    modifier: Modifier = Modifier,
    cardTypeStr: String = "CREDIT",
    onBackClick: () -> Unit = {},
    onManageCardClick: () -> Unit = {},
    onRedeemClick: () -> Unit = {}
) {
    val cardType = when (cardTypeStr) {
        "DEBIT" -> CardType.DebitCards
        "VIRTUAL" -> CardType.VirtualCards
        else -> CardType.CreditCards
    }

    // State to control card flip
    var cardFace by remember { mutableStateOf(CardFace.Front) }
    
    // State to control bottom sheet visibility
    var showBottomSheet by remember { mutableStateOf(false) }
    
    // Sheet state for Material3 ModalBottomSheet
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    GlassScaffold(
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Navigation Header with Back Button
            NavigationHeader(
                onBackClick = onBackClick
            )

            Spacer(Modifier.height(24.dp))

            // Card Details Header (Title, Subtitle, Badge)
            val headerTitle = when (cardType) {
                CardType.DebitCards -> "Debit Cards"
                CardType.VirtualCards -> "Virtual Cards"
                else -> "Credit Cards"
            }
            val headerSubtitle = when (cardType) {
                CardType.DebitCards -> "Your Debit Cards"
                CardType.VirtualCards -> "Your Virtual Cards"
                else -> "Your Credit Cards"
            }
            
            CardDetailsHeader(
                title = headerTitle,
                subtitle = headerSubtitle,
                cardCount = 1
            )

            Spacer(Modifier.height(32.dp))

            // Premium Payment Card Component with 3D Flip
            PaymentCard(
                cardFace = cardFace,
                cardType = cardType,
                cardNumber = "9012 3456 7890 1234"
            )

            Spacer(Modifier.height(24.dp))

            // Manage Card Button - Show bottom sheet on click
            ManageCardButton(
                onClick = { showBottomSheet = true }
            )

            Spacer(Modifier.height(16.dp))

            // Rewards Points Section
            RewardsPointsSection(
                points = 1250,
                onRedeemClick = onRedeemClick
            )
            
            Spacer(Modifier.height(32.dp))
            
            // Flip Card Button
            Button(
                onClick = { 
                    cardFace = if (cardFace == CardFace.Front) CardFace.Back else CardFace.Front 
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = androidx.compose.foundation.layout.PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFF9966), // Orange
                                    Color(0xFFFF5E62)  // Coral
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                        Spacer(Modifier.size(8.dp))
                        Text(
                            text = "Flip Card",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            
            Spacer(Modifier.height(24.dp))
        }
    }

    // Show bottom sheet using Material3 ModalBottomSheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = Color.Transparent,
            dragHandle = null
        ) {
            ManageCardBottomSheet(
                onDismiss = { showBottomSheet = false },
                onManageLimitsClick = { /* TODO: Handle manage limits */ },
                onManageAutoPayClick = { /* TODO: Handle manage autopay */ },
                onSetResetPinClick = { /* TODO: Handle set/reset PIN */ },
                onSmartEmiClick = { /* TODO: Handle SmartEMI */ },
                onRequestAddOnCardClick = { /* TODO: Handle add-on card */ },
                onUpgradeCardClick = { /* TODO: Handle upgrade card */ },
                onIncreaseCreditLimitClick = { /* TODO: Handle increase limit */ }
            )
        }
    }
}


@Preview(
    name = "Card Details Screen",
    showBackground = false,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
private fun CardDetailsScreenPreview() {
    CardDetailsScreen()
}
