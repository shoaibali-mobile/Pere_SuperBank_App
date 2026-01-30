package com.shoaib.cards.ui.debit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shoaib.cards.ui.details.CardDetailsContent
import com.shoaib.cards.viewmodel.debit.DebitCardDetailsViewModel

@Composable
fun DebitCardDetailsScreen(
    modifier: Modifier = Modifier,
    cardId: String,
    onBackClick: () -> Unit = {},
    onManageCardClick: () -> Unit = {},
    onRedeemClick: () -> Unit = {},
    onSetResetPinClick: () -> Unit = {},
    onSetAutopayClick: () -> Unit = {},
    onRequestAddOnCardClick: () -> Unit = {}
) {
    val viewModel: DebitCardDetailsViewModel = hiltViewModel()
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
