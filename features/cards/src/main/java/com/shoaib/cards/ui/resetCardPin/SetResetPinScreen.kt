package com.shoaib.cards.ui.resetCardPin

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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shoaib.cards.ui.resetCardPin.components.CardNumberRow
import com.shoaib.cards.ui.resetCardPin.components.ConfirmPinButton
import com.shoaib.cards.ui.resetCardPin.components.PhysicalPinInfoText
import com.shoaib.cards.ui.resetCardPin.components.PinInputSection
import com.shoaib.cards.ui.resetCardPin.components.RequestPhysicalPinButton
import com.shoaib.cards.ui.resetCardPin.components.SetResetPinTopBar
import com.shoaib.cards.ui.resetCardPin.components.TermsCheckboxRow
import com.shoaib.cards.utils.maskCardNumberForPin
import com.shoaib.cards.viewmodel.SetResetPinViewModel
import com.shoaib.cards.viewmodel.SnackbarEvent
import com.shoaib.design.components.GlassScaffold
import com.shoaib.design.components.ThemedSnackBarHost
import kotlinx.coroutines.delay

private const val PIN_LENGTH = 4

@Composable
fun SetResetPinScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val viewModel: SetResetPinViewModel = hiltViewModel()
    val card by viewModel.card.collectAsState()
    val cardNumberDisplay = card?.cardNumber?.maskCardNumberForPin() ?: "**** **** **** ****"
    GlassScaffold(modifier = modifier) { innerPadding ->
        SetResetPinScreenContent(
            viewModel = viewModel,
            cardNumberDisplay = cardNumberDisplay,
            onBackClick = onBackClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
private fun SetResetPinScreenContent(
    viewModel: SetResetPinViewModel?,
    cardNumberDisplay: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isErrorSnackbar by remember { mutableStateOf(false) }
    var pin by remember { mutableStateOf(List(PIN_LENGTH) { "" }) }
    var pinReenter by remember { mutableStateOf(List(PIN_LENGTH) { "" }) }
    var termsAccepted by remember { mutableStateOf(false) }
    val isConfirmEnabled =
        pin.all { it.length == 1 } && pinReenter.all { it.length == 1 } && pin == pinReenter && termsAccepted
    val firstPinBoxFocusRequester = remember { FocusRequester() }

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

    LaunchedEffect(Unit) {
        delay(100)
        firstPinBoxFocusRequester.requestFocus()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SetResetPinTopBar(onBackClick = onBackClick)

            Spacer(Modifier.height(24.dp))

            CardNumberRow(
                cardNumber = cardNumberDisplay,
                onCardClick = { /* TODO: card selector dropdown */ }
            )

            Spacer(Modifier.height(24.dp))

            PinInputSection(
                pin = pin,
                onPinChange = { pin = it },
                pinReenter = pinReenter,
                onPinReenterChange = { pinReenter = it },
                firstPinBoxFocusRequester = firstPinBoxFocusRequester
            )

            Spacer(Modifier.height(24.dp))

            RequestPhysicalPinButton(onClick = { /* TODO: request physical PIN */ })

            Spacer(Modifier.height(12.dp))

            PhysicalPinInfoText()

            Spacer(Modifier.height(24.dp))

            TermsCheckboxRow(
                termsAccepted = termsAccepted,
                onTermsAcceptedChange = { termsAccepted = it },
                onTermsClick = { /* TODO: open T&C */ }
            )

            Spacer(Modifier.height(24.dp))

            ConfirmPinButton(
                onClick = {
                    viewModel?.submitPin(
                        newPin = pin.joinToString(""),
                        confirmPin = pinReenter.joinToString(""),
                        termsAccepted = termsAccepted
                    )
                },
                enabled = isConfirmEnabled
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
    }
}

@Preview(
    name = "Set/Reset PIN Screen",
    showBackground = false,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
private fun SetResetPinScreenPreview() {
    GlassScaffold(modifier = Modifier) { innerPadding ->
        SetResetPinScreenContent(
            viewModel = null,
            cardNumberDisplay = "652925******7890",
            onBackClick = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}
