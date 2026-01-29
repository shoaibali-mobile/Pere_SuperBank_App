package com.shoaib.cards.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.shoaib.cards.ui.details.components.CardDetailsHeader
import com.shoaib.cards.ui.details.components.ManageCardBottomSheet
import com.shoaib.cards.ui.details.components.ManageCardButton
import com.shoaib.cards.ui.details.components.PaymentCard
import com.shoaib.cards.ui.details.components.RewardsPointsSection
import com.shoaib.cards.viewmodel.CardDetailsUiState
import com.shoaib.cards.viewmodel.CardDetailsViewModel
import com.shoaib.design.components.GlassScaffold
import com.shoaib.design.components.SuperLoading
import kotlinx.coroutines.delay

@Composable
fun CardDetailsScreen(
    modifier: Modifier = Modifier,
    cardId: String,
    onBackClick: () -> Unit = {},
    onManageCardClick: () -> Unit = {},
    onRedeemClick: () -> Unit = {},
    onSetResetPinClick: () -> Unit = {},
    onSetAutopayClick: () -> Unit = {},
    onRequestAddOnCardClick: () -> Unit = {}
) {
    val viewModel: CardDetailsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    CardDetailsContent(
        modifier = modifier,
        uiState = uiState,
        onBackClick = onBackClick,
        onManageCardClick = onManageCardClick,
        onRedeemClick = onRedeemClick,
        onSetResetPinClick = onSetResetPinClick,
        onSetAutopayClick = onSetAutopayClick,
        onRequestAddOnCardClick = onRequestAddOnCardClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailsContent(
    modifier: Modifier = Modifier,
    uiState: CardDetailsUiState,
    onBackClick: () -> Unit = {},
    onManageCardClick: () -> Unit = {},
    onRedeemClick: () -> Unit = {},
    onSetResetPinClick: () -> Unit = {},
    onSetAutopayClick: () -> Unit = {},
    onRequestAddOnCardClick: () -> Unit = {}
) {
    // State to control card flip
    var cardFace by remember { mutableStateOf(CardFace.Front) }
    
    // State to control bottom sheet visibility
    var showBottomSheet by remember { mutableStateOf(false) }
    
    // Swipe Hint State
    var showSwipeHint by remember { mutableStateOf(false) }
    
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
                val cards = uiState.cards
                val pagerState = rememberPagerState(initialPage = uiState.initialIndex) { cards.size }
                
                // Show hint if more than 1 card
                LaunchedEffect(cards) {
                    if (cards.size > 1) {
                        showSwipeHint = true
                        delay(2000) // Show for 2 seconds
                        showSwipeHint = false
                    }
                }
                
                // Get currently displayed card based on Pager State
                val currentCard = cards[pagerState.currentPage]
                
                val cardType = when (currentCard.cardType.uppercase()) {
                    "DEBIT" -> CardType.DebitCards
                    "VIRTUAL" -> CardType.VirtualCards
                    else -> CardType.CreditCards
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 16.dp) 
                ) {
                    // Navigation Header with Back Button
                    NavigationHeader(
                        onBackClick = onBackClick,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    Spacer(Modifier.height(24.dp))

                    // Card Details Header (Title, Subtitle, Badge)
                    val headerTitle = when (cardType) {
                        CardType.DebitCards -> "Debit Card"
                        CardType.VirtualCards -> "Virtual Card"
                        else -> "Credit Card"
                    }
                    
                    CardDetailsHeader(
                        title = headerTitle,
                        subtitle = currentCard.cardHolderName,
                        cardCount = cards.size, // Show total count
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    Spacer(Modifier.height(32.dp))

                    // Horizontal Pager with Swipe Hint Overlay
                    Box(
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            pageSpacing = 16.dp
                        ) { page ->
                            val card = cards[page]
                            val pageCardType = when (card.cardType.uppercase()) {
                                "DEBIT" -> CardType.DebitCards
                                "VIRTUAL" -> CardType.VirtualCards
                                else -> CardType.CreditCards
                            }
                            
                        PaymentCard(
                            cardFace = if (page == pagerState.currentPage) cardFace else CardFace.Front, // Only flip active card
                            cardType = pageCardType,
                            cardNumber = card.cardNumber,
                            cardholderName = card.cardHolderName,
                            expiryDate = "${card.expiryMonth}/${card.expiryYear.toString().takeLast(2)}",
                            cvv = card.cvv,
                            cardNetwork = card.cardType, // Pass API card type string for logo
                            cardIndex = page // Pass index for unique color
                        )
                        }

                        // Swipe Hint Animation (Arrow) - use top-level AnimatedVisibility (Box is not ColumnScope)
                        androidx.compose.animation.AnimatedVisibility(
                            visible = showSwipeHint,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                    .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Swipe to see more cards",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    // Pager Indicator (Dots)
                    if (cards.size > 1) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(cards.size) { iteration ->
                                val isActive = pagerState.currentPage == iteration
                                val color = if (isActive) Color(0xFFFF9966) else Color.White.copy(alpha = 0.3f)
                                val width = if (isActive) 24.dp else 8.dp
                                
                                Box(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .height(8.dp)
                                        .width(width)
                                        .clip(CircleShape)
                                        .background(color)
                                        .animateContentSize()
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    // Manage Card Button - Show bottom sheet on click
                    ManageCardButton(
                        onClick = { showBottomSheet = true },
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    // Rewards Points Section
                    RewardsPointsSection(
                        points = currentCard.rewardsPoints,
                        onRedeemClick = onRedeemClick,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    
                    Spacer(Modifier.height(32.dp))
                    
                    // Flip Card Button
                    Button(
                        onClick = { 
                            cardFace = if (cardFace == CardFace.Front) CardFace.Back else CardFace.Front 
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .height(56.dp)
                            .clip(RoundedCornerShape(28.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues()
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
                onManageLimitsClick = {
                    showBottomSheet = false
                    onManageCardClick()
                },
                onManageAutoPayClick = {
                    showBottomSheet = false
                    onSetAutopayClick()
                },
                onSetResetPinClick = {
                    showBottomSheet = false   // close sheet
                    onSetResetPinClick()
                },
                onSmartEmiClick = { /* TODO: Handle SmartEMI */ },
                onRequestAddOnCardClick = {
                    showBottomSheet = false
                    onRequestAddOnCardClick()
                },
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
        modifier = Modifier,
        uiState = CardDetailsUiState.Success(listOf(fakeCard), 0),
        onBackClick = {},
        onManageCardClick = {},
        onRedeemClick = {},
        onSetResetPinClick = {},
        onSetAutopayClick = {},
        onRequestAddOnCardClick = {}
    )
}
