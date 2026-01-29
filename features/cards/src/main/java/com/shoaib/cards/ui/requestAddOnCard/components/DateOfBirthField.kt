package com.shoaib.cards.ui.requestAddOnCard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.design.theme.SuperAppDesign
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DATE_FORMAT = "yyyy-MM-dd"
private val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.US)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateOfBirthField(
    value: String,
    onDateChange: (String) -> Unit,
    label: String = "Date of birth",
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val initialMillis = remember(value) {
        if (value.isNotBlank()) {
            try {
                dateFormatter.parse(value)?.time
            } catch (_: Exception) {
                null
            }
        } else null
    }

    val datePickerState = key(value) {
        rememberDatePickerState(
            initialSelectedDateMillis = initialMillis,
            initialDisplayedMonthMillis = initialMillis
        )
    }

    OutlinedTextField(
        value = value,
        onValueChange = { },
        readOnly = true,
        label = { Text(label, color = SuperAppDesign.TextSecondary, fontSize = 14.sp) },
        leadingIcon = {
            androidx.compose.material3.IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = "Pick date",
                    tint = SuperAppDesign.TextSecondary
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { showDialog = true },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SuperAppDesign.GlassBorder,
            unfocusedBorderColor = SuperAppDesign.GlassBorder,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color(0xFFFF9966),
            focusedLabelColor = SuperAppDesign.TextSecondary,
            unfocusedLabelColor = SuperAppDesign.TextSecondary
        ),
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    )

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            onDateChange(dateFormatter.format(Date(millis)))
                        }
                        showDialog = false
                    }
                ) {
                    Text("OK", color = Color(0xFFFF9966))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel", color = SuperAppDesign.TextSecondary)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
