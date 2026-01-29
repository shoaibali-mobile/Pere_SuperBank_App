package com.shoaib.cards.ui.setAutoPay.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.design.theme.SuperAppDesign

@Composable
fun AutoPayToggleRow(
    autoPayEnabled: Boolean,
    onAutoPayEnabledChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SuperAppDesign.GlassWhite)
            .border(1.dp, SuperAppDesign.GlassBorder, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
    ) {
        Text(
            text = "AutoPay",
            color = SuperAppDesign.TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Switch(
            checked = autoPayEnabled,
            onCheckedChange = onAutoPayEnabledChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = SuperAppDesign.TextPrimary,
                checkedTrackColor = SuperAppDesign.GlassBorder,
                uncheckedThumbColor = SuperAppDesign.TextSecondary,
                uncheckedTrackColor = SuperAppDesign.GlassWhite
            )
        )
    }
}
