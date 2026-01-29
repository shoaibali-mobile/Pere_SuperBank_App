package com.shoaib.cards.ui.resetCardPin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoaib.cards.R

// --- Design tokens (this section only) ---
private val PhysicalPinButtonBlue = Color(0xFF2196F3)
private val PhysicalPinContentWhite = Color.White
private val CornerRadius = 16.dp
private val ContentPadding = 16.dp
private val IconSize = 24.dp
private val SpacingBetweenIconAndText = 12.dp
private val MinTouchTargetHeight = 48.dp

/** Label for the single CTA; reuse for semantics and content. */
private const val RequestPhysicalPinLabel = "Request for a Physical PIN"

/**
 * Primary CTA to request a physical PIN by post.
 * Solid blue button with icon and label; use only on Set/Reset PIN screen.
 *
 * @param onClick Called when the button is pressed
 * @param modifier Modifier for the root layout
 */
@Composable
fun RequestPhysicalPinButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CornerRadius))
            .background(PhysicalPinButtonBlue)
            .heightIn(min = MinTouchTargetHeight)
            .semantics(mergeDescendants = true) {
                role = Role.Button
                contentDescription = RequestPhysicalPinLabel
            }
            .clickable(role = Role.Button, onClick = onClick)
            .padding(ContentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.lock),
            contentDescription = null,
            tint = PhysicalPinContentWhite,
            modifier = Modifier.size(IconSize)
        )
        Spacer(Modifier.width(SpacingBetweenIconAndText))
        Text(
            text = RequestPhysicalPinLabel,
            color = PhysicalPinContentWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = PhysicalPinContentWhite,
            modifier = Modifier.size(IconSize)
        )
    }
}
