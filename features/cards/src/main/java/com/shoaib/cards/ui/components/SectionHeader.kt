package com.shoaib.cards.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.design.theme.SuperAppDesign

/**
 * Section Header Component
 * Displays section title and subtitle
 */
@Composable
fun SectionHeader() {
    Column {
        Text(
            text = "Your Cards",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = SuperAppDesign.TextPrimary
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Manage and view your payment cards",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = SuperAppDesign.TextSecondary
        )
    }
}
