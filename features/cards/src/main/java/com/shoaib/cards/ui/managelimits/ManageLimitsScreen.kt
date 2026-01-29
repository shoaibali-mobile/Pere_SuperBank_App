package com.shoaib.cards.ui.managelimits

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shoaib.cards.model.managelimit.CardLimitData
import com.shoaib.cards.ui.components.NavigationHeader
import com.shoaib.cards.utils.maskCardNumber
import com.shoaib.cards.ui.managelimits.components.LimitItemRow
import com.shoaib.cards.viewmodel.ManageLimitsUiState
import com.shoaib.cards.viewmodel.ManageLimitsViewModel
import com.shoaib.design.components.GlassScaffold
import com.shoaib.design.components.SuperLoading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

@Composable
fun ManageLimitsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val viewModel: ManageLimitsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val card by viewModel.card.collectAsState()

    var selectedTab by remember { mutableStateOf(0) } // 0: Domestic, 1: International
    var editingLimitIndex by remember { mutableStateOf<Int?>(null) }

    GlassScaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            NavigationHeader(onBackClick = onBackClick)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Manage Limits",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            card?.let { c ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${c.cardType}: ${c.cardNumber.maskCardNumber(4)}",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            // Tab Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.1f))
            ) {
                TabButton(
                    text = "Domestic",
                    isSelected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        editingLimitIndex = null
                    },
                    modifier = Modifier.weight(1f)
                )
                TabButton(
                    text = "International",
                    isSelected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        editingLimitIndex = null
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            when (val state = uiState) {
                is ManageLimitsUiState.Loading -> {
                    SuperLoading(message = "Fetching limits...")
                }
                is ManageLimitsUiState.Error -> {
                    Text(text = state.message, color = Color.Red)
                }
                is ManageLimitsUiState.Success -> {
                    // Initialize local state with fetched limits
                    // using remember(state.limits) ensures it resets if source data changes (e.g. after save)
                    var currentLimits by remember(state.limits) { mutableStateOf(state.limits) }
                    
                    // Defensive check: Ensure lists are not null (Gson safety)
                    val limitsList = if (selectedTab == 0) {
                        currentLimits.domesticLimits ?: emptyList()
                    } else {
                        currentLimits.internationalLimits ?: emptyList()
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        limitsList.forEachIndexed { index, limit ->
                            LimitItemRow(
                                limit = limit,
                                isEditing = editingLimitIndex == index,
                                onSetLimitClick = { editingLimitIndex = index },
                                onToggle = { isEnabled ->
                                    val updatedLimit = limit.copy(isEnabled = isEnabled)
                                    val updatedList = limitsList.toMutableList().apply {
                                        set(index, updatedLimit)
                                    }
                                    if (!isEnabled) editingLimitIndex = null
                                    currentLimits = if (selectedTab == 0) {
                                        currentLimits.copy(domesticLimits = updatedList)
                                    } else {
                                        currentLimits.copy(internationalLimits = updatedList)
                                    }
                                },
                                onValueChange = { newValue ->
                                    val updatedLimit = limit.copy(currentLimit = newValue)
                                    val updatedList = limitsList.toMutableList().apply {
                                        set(index, updatedLimit)
                                    }
                                    currentLimits = if (selectedTab == 0) {
                                        currentLimits.copy(domesticLimits = updatedList)
                                    } else {
                                        currentLimits.copy(internationalLimits = updatedList)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Save Button
                    val hasChanges = currentLimits != state.limits
                    
                    Button(
                        onClick = {
                            editingLimitIndex = null
                            viewModel.updateLimits(currentLimits)
                        },
                        enabled = hasChanges,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF9966),
                            disabledContainerColor = Color.White.copy(alpha = 0.1f),
                            contentColor = Color.White,
                            disabledContentColor = Color.White.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Update",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Color.White else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.Black else Color.White.copy(alpha = 0.7f),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}
