package com.shoaib.cards.ui.credit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shoaib.cards.ui.components.NavigationHeader
import com.shoaib.cards.ui.credit.components.CreditCard
import com.shoaib.cards.ui.credit.components.CreditCardsHeader
import com.shoaib.cards.ui.credit.components.ManageCardBottomSheet
import com.shoaib.cards.ui.credit.components.ManageCardButton
import com.shoaib.cards.ui.credit.components.RewardsPointsSection
import com.shoaib.design.components.GlassScaffold
import com.shoaib.design.theme.SuperAppDesign


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditCardScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onManageCardClick: () -> Unit = {},
    onRedeemClick: () -> Unit = {}
) {
    // State to control bottom sheet visibility
    var showBottomSheet by remember { mutableStateOf(false) }
    
    // Sheet state for Material3 ModalBottomSheet
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // Dismisses immediately when dragged down
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

            // Credit Cards Header (Title, Subtitle, Badge)
            CreditCardsHeader(
                cardCount = 1
            )

            Spacer(Modifier.height(32.dp))

            // Premium Credit Card Component
            CreditCard(
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
        }
    }

    // Show bottom sheet using Material3 ModalBottomSheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = Color.Transparent, // Transparent so the sheet's own background shows
            dragHandle = null // Remove default drag handle
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
    name = "Credit Card Details Screen",
    showBackground = false,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
private fun CreditCardScreenPreview() {
    CreditCardScreen()
}
