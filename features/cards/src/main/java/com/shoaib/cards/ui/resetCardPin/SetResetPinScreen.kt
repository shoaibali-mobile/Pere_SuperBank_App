package com.shoaib.cards.ui.resetCardPin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shoaib.cards.ui.components.NavigationHeader
import com.shoaib.cards.ui.resetCardPin.components.CardNumberRow
import com.shoaib.cards.ui.resetCardPin.components.ConfirmPinButton
import com.shoaib.cards.ui.resetCardPin.components.PhysicalPinInfoText
import com.shoaib.cards.ui.resetCardPin.components.PinInputSection
import com.shoaib.cards.ui.resetCardPin.components.RequestPhysicalPinButton
import com.shoaib.cards.ui.resetCardPin.components.TermsCheckboxRow
import com.shoaib.cards.utils.maskCardNumberForPin
import com.shoaib.cards.viewmodel.SetResetPinViewModel
import com.shoaib.design.components.GlassScaffold

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
            cardNumberDisplay = cardNumberDisplay,
            onBackClick = onBackClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun SetResetPinScreenContent(
    cardNumberDisplay: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pin by remember { mutableStateOf(List(PIN_LENGTH) { "" }) }
    var pinReenter by remember { mutableStateOf(List(PIN_LENGTH) { "" }) }
    var termsAccepted by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        NavigationHeader(onBackClick = onBackClick)

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Set/Reset PIN",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

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
            onPinReenterChange = { pinReenter = it }
        )

        Spacer(Modifier.height(24.dp))

        RequestPhysicalPinButton(
            onClick = { /* TODO: request physical PIN */ }
        )

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
            onClick = { /* TODO: confirm PIN reset */ }
        )

        Spacer(Modifier.height(32.dp))
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
            cardNumberDisplay = "652925******7890",
            onBackClick = {},
            modifier = Modifier.padding(innerPadding)
        )
    }
}
