package com.shoaib.cards.ui.resetCardPin.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shoaib.design.theme.SuperAppDesign

@Composable
fun TermsCheckboxRow(
    termsAccepted: Boolean,
    onTermsAcceptedChange: (Boolean) -> Unit,
    onTermsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = termsAccepted,
            onCheckedChange = onTermsAcceptedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF2196F3),
                uncheckedColor = SuperAppDesign.TextSecondary
            )
        )
        Text(
            text = "I have read and understood the ",
            color = Color.White,
            fontSize = 14.sp
        )
        Text(
            text = "Terms & Conditions",
            color = Color(0xFF2196F3),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable(onClick = onTermsClick)
        )
    }
}
