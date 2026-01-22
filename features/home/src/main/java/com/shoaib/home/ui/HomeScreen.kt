package com.shoaib.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material.icons.outlined.WifiTethering
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shoaib.design.components.GlassScaffold
import com.shoaib.design.theme.SuperAppDesign
import com.shoaib.home.model.DashboardItem
import com.shoaib.home.viewmodel.HomeViewModel
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,

    items: List<DashboardItem> = defaultDashboardItems(),
    viewModel: HomeViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsStateWithLifecycle()
    
    // Extract display name in UI layer
    val displayName = user?.name
        ?.takeIf { it.isNotBlank() }
        ?.let { "Hi, $it" }
        ?: "Hi, there"

    GlassScaffold(
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            HeaderSection(displayName = displayName)
            Spacer(Modifier.height(24.dp))
            DashboardGrid(items)
        }
    }
}

@Composable
private fun HeaderSection(displayName: String) {
    Text(
        text = displayName,
        fontSize = 42.sp,
        fontWeight = FontWeight.ExtraBold,
        color = SuperAppDesign.TextPrimary,
        letterSpacing = (-1).sp
    )
    Spacer(Modifier.height(20.dp))
    
    Text(
        text = "Welcome",
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        color = SuperAppDesign.TextPrimary
    )
    Spacer(Modifier.height(8.dp))
    
    Text(
        text = "Manage your financial services",
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        color = SuperAppDesign.TextSecondary
    )
}

@Composable
private fun DashboardGrid(items: List<DashboardItem>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) { item ->
            DashboardCard(item)
        }
    }
}

@Composable
private fun DashboardCard(item: DashboardItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = SuperAppDesign.GlassWhite
            )
            .border(
                width = 1.dp,
                color = SuperAppDesign.GlassBorder,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = item.onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = item.tint,
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.height(12.dp))
            
            Text(
                text = item.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = SuperAppDesign.TextPrimary,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun defaultDashboardItems(): List<DashboardItem> = listOf(
    DashboardItem(
        title = "Profile",
        icon = Icons.Outlined.Person,
        tint = Color(0xFF60A5FA) // Light blue
    ),
    DashboardItem(
        title = "Banking Services",
        icon = Icons.Outlined.AccountBalance,
        tint = Color(0xFF3B82F6) // Blue
    ),
    DashboardItem(
        title = "Cards & Payments",
        icon = Icons.Outlined.CreditCard,
        tint = Color(0xFFA855F7) // Purple
    ),
    DashboardItem(
        title = "Rewards & Shopping",
        icon = Icons.Outlined.CardGiftcard,
        tint = Color(0xFFF59E0B) // Orange
    ),
    DashboardItem(
        title = "Digital Payments",
        icon = Icons.Outlined.WifiTethering,
        tint = Color(0xFF22C55E) // Green
    ),
    DashboardItem(
        title = "Subscriptions\n& Security",
        icon = Icons.Outlined.VerifiedUser,
        tint = Color(0xFFEF4444) // Red
    )
)

// ========== PREVIEWS ==========

@Preview(
    name = "Dashboard Screen - Full Preview",
    showBackground = false,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}

@Preview(
    name = "Dashboard Card Preview",
    showBackground = false,
    widthDp = 180,
    heightDp = 140
)
@Composable
private fun DashboardCardPreview() {
    val sampleItem = DashboardItem(
        title = "Banking Services",
        icon = Icons.Outlined.AccountBalance,
        tint = Color(0xFF3B82F6)
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = SuperAppDesign.backgroundBrush)
            .padding(16.dp)
    ) {
        DashboardCard(sampleItem)
    }
}

@Preview(
    name = "Header Section Preview",
    showBackground = false,
    widthDp = 411
)
@Composable
private fun HeaderSectionPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = SuperAppDesign.backgroundBrush)
            .padding(20.dp)
    ) {
        HeaderSection(displayName = "Hi, Shoaib")
    }
}
