package com.shoaib.cards.ui.setAutoPay

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shoaib.cards.ui.setAutoPay.components.AutoPayToggleRow
import com.shoaib.cards.ui.setAutoPay.components.CardTypeRow
import com.shoaib.cards.ui.setAutoPay.components.SaveAutopayButton
import com.shoaib.cards.ui.setAutoPay.components.SelectBillerSection
import com.shoaib.cards.ui.setAutoPay.components.SetAutopayTopBar
import com.shoaib.cards.utils.maskCardNumber
import com.shoaib.cards.viewmodel.SetAutopayViewModel
import com.shoaib.cards.viewmodel.SnackbarEvent
import com.shoaib.design.components.GlassScaffold
import com.shoaib.design.components.SuperLoading
import com.shoaib.design.components.ThemedSnackBarHost
import kotlinx.coroutines.flow.flowOf

@Composable
fun SetAutopayScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val viewModel: SetAutopayViewModel = hiltViewModel()
    val card by viewModel.card.collectAsState()
    val cardTypeDisplay = card?.cardType ?: "Card"
    val maskedNumber = card?.cardNumber?.maskCardNumber(4) ?: "**** **** **** ****"
    GlassScaffold(modifier = modifier) { innerPadding ->
        SetAutopayContent(
            viewModel = viewModel,
            cardTypeDisplay = cardTypeDisplay,
            maskedNumber = maskedNumber,
            onBackClick = onBackClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
private fun SetAutopayContent(
    viewModel: SetAutopayViewModel?,
    cardTypeDisplay: String,
    maskedNumber: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isErrorSnackbar by remember { mutableStateOf(false) }
    var autoPayEnabled by remember { mutableStateOf(false) }
    var billerSectionExpanded by remember { mutableStateOf(false) }
    var selectedBiller by remember { mutableStateOf<String?>(null) }
    val isLoading by (viewModel?.isLoading ?: flowOf(false)).collectAsState(initial = false)

    LaunchedEffect(viewModel) {
        viewModel ?: return@LaunchedEffect
        viewModel.snackbarEvent.collect { event ->
            when (event) {
                is SnackbarEvent.Success -> {
                    isErrorSnackbar = false
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is SnackbarEvent.Error -> {
                    isErrorSnackbar = true
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SetAutopayTopBar(onBackClick = onBackClick)

            Spacer(Modifier.height(24.dp))

            CardTypeRow(
                cardTypeDisplay = cardTypeDisplay,
                maskedNumber = maskedNumber
            )

            Spacer(Modifier.height(24.dp))

            AutoPayToggleRow(
                autoPayEnabled = autoPayEnabled,
                onAutoPayEnabledChange = { autoPayEnabled = it }
            )

            Spacer(Modifier.height(24.dp))

            SelectBillerSection(
                expanded = billerSectionExpanded,
                onExpandChange = { billerSectionExpanded = it },
                selectedBiller = selectedBiller,
                onBillerSelected = { selectedBiller = it }
            )

            Spacer(Modifier.height(24.dp))

            SaveAutopayButton(
                onClick = {
                    viewModel?.submitAutopay(
                        amountOption = selectedBiller!!,
                        autoPayEnabled = autoPayEnabled
                    )
                },
                enabled = autoPayEnabled && selectedBiller != null
            )

            Spacer(Modifier.height(32.dp))
        }

        ThemedSnackBarHost(
            hostState = snackbarHostState,
            isError = isErrorSnackbar,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        if (isLoading) {
            SuperLoading(
                modifier = Modifier.fillMaxSize(),
                message = "Updating autopay..."
            )
        }
    }
}
