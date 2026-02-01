package com.shoaib.cards.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shoaib.cards.viewmodel.CardDetailsViewModel

@Composable
fun CardDetailsScreen(
    modifier: Modifier = Modifier,
    cardId: String,
    onBackClick: () -> Unit = {},
    onManageCardClick: (String) -> Unit = {},
    onRedeemClick: () -> Unit = {},
    onSetResetPinClick: (String) -> Unit = {},
    onSetAutopayClick: (String) -> Unit = {},
    onRequestAddOnCardClick: (String) -> Unit = {}
) {
    val viewModel: CardDetailsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
