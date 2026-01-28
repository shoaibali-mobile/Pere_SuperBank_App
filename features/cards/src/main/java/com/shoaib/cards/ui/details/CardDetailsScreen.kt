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
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shoaib.cards.model.CardFace
import com.shoaib.cards.model.CardType
import com.shoaib.cards.model.CreditCardDto
import com.shoaib.cards.ui.components.NavigationHeader
import com.shoaib.cards.ui.details.components.PaymentCard
import com.shoaib.cards.ui.details.components.CardDetailsHeader
import com.shoaib.cards.ui.details.components.ManageCardBottomSheet
import com.shoaib.cards.ui.details.components.ManageCardButton
import com.shoaib.cards.ui.details.components.RewardsPointsSection
import com.shoaib.cards.viewmodel.CardDetailsUiState
import com.shoaib.cards.viewmodel.CardDetailsViewModel
import com.shoaib.design.components.GlassScaffold
import com.shoaib.design.components.SuperLoading

@Composable
fun CardDetailsScreen(
    modifier: Modifier = Modifier,
    cardId: String,
    onBackClick: () -> Unit = {},
    onManageCardClick: () -> Unit = {},
    onRedeemClick: () -> Unit = {}
) {
    val viewModel: CardDetailsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    CardDetailsContent(
        modifier = modifier,
        uiState = uiState,
        onBackClick = onBackClick,
        onManageCardClick = onManageCardClick,
        onRedeemClick = onRedeemClick
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailsContent(
    modifier: Modifier = Modifier,
    uiState: CardDetailsUiState,
    onBackClick: () -> Unit = {},
    onManageCardClick: () -> Unit = {},
    onRedeemClick: () -> Unit = {}
) {
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
        when (uiState) {
            is CardDetailsUiState.Loading -> {
                SuperLoading(
                    message = "Fetching card details...",
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is CardDetailsUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Error loading card",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.message,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onBackClick) {
                            Text("Go Back")
                        }
                    }
                }
            }
            is CardDetailsUiState.Success -> {
                val card = uiState.card
                val cardType = when (card.cardType.uppercase()) {
                    "DEBIT" -> CardType.DebitCards
                    "VIRTUAL" -> CardType.VirtualCards
                    else -> CardType.CreditCards
                }

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
                        CardType.DebitCards -> "Debit Card"
                        CardType.VirtualCards -> "Virtual Card"
                        else -> "Credit Card"
                    }
                    val headerSubtitle = card.cardHolderName
                    
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
                        cardNumber = card.cardNumber,
                        cardholderName = card.cardHolderName,
                        expiryDate = "${card.expiryMonth}/${card.expiryYear.toString().takeLast(2)}",
                        cvv = card.cvv
                    )

                    Spacer(Modifier.height(24.dp))

                    // Manage Card Button - Show bottom sheet on click
                    ManageCardButton(
                        onClick = { showBottomSheet = true }
                    )

                    Spacer(Modifier.height(16.dp))

                    // Rewards Points Section
                    RewardsPointsSection(
                        points = card.rewardsPoints,
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
    val fakeCard = CreditCardDto(
        id = "123",
        cardNumber = "4532123456789012",
        cardType = "VISA",
        cardHolderName = "John Doe",
        cvv = "123",
        expiryMonth = 12,
        expiryYear = 2026,
        rewardsPoints = 1250
    )
    
    CardDetailsContent(
        uiState = CardDetailsUiState.Success(fakeCard)
    )
}
