package com.shoaib.cards.ui.resetCardPin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.design.theme.SuperAppDesign

private const val PIN_LENGTH = 4

@Composable
fun PinInputSection(
    pin: List<String>,
    onPinChange: (List<String>) -> Unit,
    pinReenter: List<String>,
    onPinReenterChange: (List<String>) -> Unit,
    firstPinBoxFocusRequester: FocusRequester? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SuperAppDesign.GlassWhite)
            .border(1.dp, SuperAppDesign.GlassBorder, RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {
        Text(
            text = "Enter New Credit Card PIN",
            color = SuperAppDesign.TextSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        PinDigitRow(
            digits = pin,
            onDigitsChange = onPinChange,
            pinLength = PIN_LENGTH,
            firstBoxFocusRequester = firstPinBoxFocusRequester
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Re-enter New Credit Card PIN",
            color = SuperAppDesign.TextSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        PinDigitRow(
            digits = pinReenter,
            onDigitsChange = onPinReenterChange,
            pinLength = PIN_LENGTH
        )
    }
}

@Composable
fun PinDigitRow(
    digits: List<String>,
    onDigitsChange: (List<String>) -> Unit,
    pinLength: Int = PIN_LENGTH,
    firstBoxFocusRequester: FocusRequester? = null,
    modifier: Modifier = Modifier
) {
    val focusRequesters = remember { List(pinLength) { FocusRequester() } }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(pinLength) { index ->
            val currentDigit = digits.getOrElse(index) { "" }
            val boxFocusRequester = if (index == 0 && firstBoxFocusRequester != null) {
                firstBoxFocusRequester
            } else {
                focusRequesters[index]
            }
            LaunchedEffect(currentDigit) {
                when {
                    currentDigit.length == 1 && index < pinLength - 1 ->
                        focusRequesters[index + 1].requestFocus()
                    currentDigit.isEmpty() && index > 0 -> {
                        val previousRequester = if (index == 1 && firstBoxFocusRequester != null) {
                            firstBoxFocusRequester
                        } else {
                            focusRequesters[index - 1]
                        }
                        previousRequester.requestFocus()
                    }
                }
            }
            PinDigitBox(
                value = digits.getOrElse(index) { "" },
                onValueChange = { s ->
                    val newDigits = digits.toMutableList()
                    newDigits[index] = s.filter { it.isDigit() }.take(1)
                    onDigitsChange(newDigits)
                },
                focusRequester = boxFocusRequester,
                modifier = Modifier
                    .width(56.dp)
                    .height(56.dp)
            )
        }
    }
}

@Composable
fun PinDigitBox(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SuperAppDesign.GlassWhite)
            .border(
                1.dp,
                SuperAppDesign.GlassBorder,
                RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = value,
            onValueChange = { text ->
                onValueChange(text.filter { it.isDigit() }.take(1))
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            textStyle = TextStyle(
                color = SuperAppDesign.TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Monospace
            ),
            cursorBrush = SolidColor(Color.Cyan),
            decorationBox = { inner ->
                Box(contentAlignment = Alignment.Center) {
                    if (value.isEmpty()) {
                        Text(
                            text = "•",
                            color = SuperAppDesign.TextHint,
                            fontSize = 24.sp
                        )
                    }
                    inner()
                }
            }
        )
    }
}
