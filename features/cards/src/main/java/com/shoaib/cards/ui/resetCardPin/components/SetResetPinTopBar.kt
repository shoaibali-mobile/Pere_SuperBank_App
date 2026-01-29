package com.shoaib.cards.ui.resetCardPin.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.design.theme.SuperAppDesign

private val TopBarTopPadding = 24.dp
private val TopBarHorizontalPadding = 16.dp
private val BackIconSize = 28.dp
private const val MinTouchTargetSizeDp = 48

@Composable
fun SetResetPinTopBar(
    onBackClick: () -> Unit,
    title: String = "Set/Reset PIN",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = TopBarTopPadding,
                start = TopBarHorizontalPadding,
                end = TopBarHorizontalPadding,
                bottom = 16.dp
            )
    ) {
        Text(
            text = title,
            color = SuperAppDesign.TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(MinTouchTargetSizeDp.dp),
            content = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = SuperAppDesign.TextPrimary,
                    modifier = Modifier.size(BackIconSize)
                )
            }
        )
    }
}
