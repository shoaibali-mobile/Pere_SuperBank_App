package com.shoaib.cards.ui.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.cards.R
import com.shoaib.design.theme.SuperAppDesign

/**
 * Manage Card Bottom Sheet Modal
 * Displays card management options in a bottom sheet
 */
@Composable
fun ManageCardBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onManageLimitsClick: () -> Unit = {},
    onManageAutoPayClick: () -> Unit = {},
    onSetResetPinClick: () -> Unit = {},
    onSmartEmiClick: () -> Unit = {},
    onRequestAddOnCardClick: () -> Unit = {},
    onUpgradeCardClick: () -> Unit = {},
    onIncreaseCreditLimitClick: () -> Unit = {}
) {
    val actionItems = listOf(
        ActionItem(
            iconRes = R.drawable.manage_limits,
            title = "Manage Limits",
            onClick = onManageLimitsClick
        ),
        ActionItem(
            iconRes = R.drawable.auto_pay,
            title = "Manage AutoPay",
            onClick = onManageAutoPayClick
        ),
        ActionItem(
            iconRes = R.drawable.lock,
            title = "Set/Reset PIN",
            onClick = onSetResetPinClick
        )
    )

    val listOptions = listOf(
        OptionItem(
            title = "SmartEMI",
            subtitle = "Convert your recent transactions to easy EMIs",
            onClick = onSmartEmiClick
        ),
        OptionItem(
            title = "Request Add-On Card",
            subtitle = "Get an add-on Credit Card for your family",
            onClick = onRequestAddOnCardClick
        ),
        OptionItem(
            title = "Upgrade Card",
            subtitle = "Upgrade your existing Credit Card",
            onClick = onUpgradeCardClick
        ),
        OptionItem(
            title = "Increase Credit Limit",
            subtitle = "Enjoy greater flexibility",
            onClick = onIncreaseCreditLimitClick
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(
                brush = SuperAppDesign.backgroundBrush,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .border(
                width = 1.dp,
                color = SuperAppDesign.GlassBorder,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header: Title and Close Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Manage Card",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SuperAppDesign.TextPrimary
                )
                
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable(onClick = onDismiss),
                    tint = SuperAppDesign.TextPrimary
                )
            }
            
            Spacer(Modifier.height(24.dp))
            
            // Action Icons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                actionItems.forEach { item ->
                    ActionIconItem(
                        iconRes = item.iconRes,
                        title = item.title,
                        onClick = item.onClick
                    )
                }
            }
            
            Spacer(Modifier.height(32.dp))
            
            // List Options
            listOptions.forEach { option ->
                ManageCardOptionItem(
                    title = option.title,
                    subtitle = option.subtitle,
                    onClick = option.onClick
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

private data class ActionItem(
    val iconRes: Int,
    val title: String,
    val onClick: () -> Unit
)

private data class OptionItem(
    val title: String,
    val subtitle: String,
    val onClick: () -> Unit
)

@Composable
private fun ActionIconItem(
    iconRes: Int,
    title: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(
                    color = Color.White.copy(alpha = 0.15f),
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(36.dp)
            )
        }
        
        Spacer(Modifier.height(8.dp))
        
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = SuperAppDesign.TextPrimary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ManageCardOptionItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = SuperAppDesign.TextPrimary
            )
            
            Spacer(Modifier.height(4.dp))
            
            Text(
                text = subtitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = SuperAppDesign.TextSecondary
            )
        }
        
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = "Navigate",
            tint = SuperAppDesign.TextSecondary,
            modifier = Modifier.size(20.dp)
        )
    }
}

// ========== PREVIEWS ==========

@Preview(
    name = "Manage Card Bottom Sheet",
    showBackground = false,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
private fun ManageCardBottomSheetPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = SuperAppDesign.backgroundBrush)
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        ManageCardBottomSheet()
    }
}
