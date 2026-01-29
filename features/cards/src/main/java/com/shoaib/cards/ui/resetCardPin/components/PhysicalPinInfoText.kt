package com.shoaib.cards.ui.resetCardPin.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.design.theme.SuperAppDesign

@Composable
fun PhysicalPinInfoText(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "We'll deliver the PIN at your registered communication address.",
            color = SuperAppDesign.TextSecondary,
            fontSize = 12.sp
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "You can also visit your nearest HDFC Bank ATM to set/reset PIN.",
            color = SuperAppDesign.TextSecondary,
            fontSize = 12.sp
        )
    }
}
