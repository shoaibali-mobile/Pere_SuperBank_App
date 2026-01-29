package com.shoaib.cards.ui.resetCardPin.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.design.theme.SuperAppDesign

// Primary action color used on Manage Limits and other screens
private val PrimaryAction = Color(0xFFFF9966)


@Composable
fun TermsLinkText(
    text: String = "Terms & Conditions",
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = PrimaryAction,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        modifier = modifier.clickable(onClick = onClick)
    )
}

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
                checkedColor = PrimaryAction,
                uncheckedColor = SuperAppDesign.TextSecondary
            ),
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(8.dp))
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "I have read and understood the ",
                color = SuperAppDesign.TextPrimary,
                fontSize = 14.sp
            )
            TermsLinkText(onClick = onTermsClick)
        }
    }
}
