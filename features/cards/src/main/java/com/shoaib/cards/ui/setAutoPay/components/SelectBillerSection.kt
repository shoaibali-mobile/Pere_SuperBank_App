package com.shoaib.cards.ui.setAutoPay.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.design.theme.SuperAppDesign

private val defaultBillers = listOf(
    "Netflix",
    "Disney+",
    "Amazon Prime",
    "Spotify",
    "YouTube Premium",
    "Apple Music"
)

@Composable
fun SelectBillerSection(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    selectedBiller: String? = null,
    onBillerSelected: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SuperAppDesign.GlassWhite)
                .border(1.dp, SuperAppDesign.GlassBorder, RoundedCornerShape(16.dp))
                .clickable { onExpandChange(!expanded) }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedBiller ?: "Select biller",
                color = SuperAppDesign.TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (expanded) "Collapse" else "Expand",
                tint = SuperAppDesign.TextSecondary,
                modifier = Modifier.size(24.dp)
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SuperAppDesign.GlassWhite)
                    .border(1.dp, SuperAppDesign.GlassBorder, RoundedCornerShape(16.dp))
                    .padding(vertical = 8.dp)
            ) {
                defaultBillers.forEach { biller ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onBillerSelected(biller)
                                onExpandChange(false)
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = biller,
                            color = SuperAppDesign.TextPrimary,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}
