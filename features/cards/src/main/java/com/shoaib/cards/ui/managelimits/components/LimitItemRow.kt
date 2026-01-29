package com.shoaib.cards.ui.managelimits.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.cards.model.managelimit.LimitItem
import com.shoaib.design.theme.SuperAppDesign

import androidx.compose.foundation.border

@Composable
fun LimitItemRow(
    limit: LimitItem,
    isEditing: Boolean = false,
    onSetLimitClick: () -> Unit = {},
    onToggle: (Boolean) -> Unit,
    onValueChange: (Double) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SuperAppDesign.GlassWhite)
            .border(
                width = 1.dp,
                color = SuperAppDesign.GlassBorder,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = limit.type,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = if (limit.isEnabled) "Limit enabled" else "Disabled",
                    color = SuperAppDesign.TextSecondary,
                    fontSize = 12.sp
                )
            }

            Switch(
                checked = limit.isEnabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFFFF9966), // Orange
                    uncheckedThumbColor = Color.LightGray,
                    uncheckedTrackColor = Color.DarkGray
                )
            )
        }

        if (limit.isEnabled && limit.canSetLimit) {
            Spacer(modifier = Modifier.padding(top = 16.dp))
            if (isEditing) {
                OutlinedTextField(
                    value = if (limit.currentLimit == 0.0) "" else limit.currentLimit.toInt().toString(),
                    onValueChange = { input ->
                        if (input.isEmpty()) {
                            onValueChange(0.0)
                        } else if (input.all { char -> char.isDigit() }) {
                            val newValue = input.toDoubleOrNull() ?: 0.0
                            if (newValue <= limit.maxLimit) {
                                onValueChange(newValue)
                            } else {
                                onValueChange(limit.maxLimit)
                            }
                        }
                    },
                    label = { Text("Set Limit", color = SuperAppDesign.TextSecondary) },
                    supportingText = {
                        Text(
                            text = "The maximum limit is ₹${limit.maxLimit.toInt()}",
                            color = SuperAppDesign.TextSecondary.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF9966),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color(0xFFFF9966)
                    ),
                    textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "₹${limit.currentLimit.toInt()}",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedButton(
                        onClick = onSetLimitClick,
                        colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF64B5F6),
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text("Set Limit", color = Color(0xFF64B5F6), fontSize = 14.sp)
                    }
                }
                Text(
                    text = "The maximum limit is ₹${limit.maxLimit.toInt()}",
                    color = SuperAppDesign.TextSecondary.copy(alpha = 0.7f),
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
