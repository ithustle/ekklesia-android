package com.toquemedia.ekklesia.ui.screens.bible

// For network image loading, add the Coil dependency to your app's build.gradle.kts:
// implementation("io.coil-kt:coil-compose:2.5.0")
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.rounded.ChatBubble
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.SignalWifiStatusbar4Bar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

// --- DATA MODELS AND MOCK DATA ---

enum class StatusType {
    RECENT, VIEWED
}

data class Status(
    val id: String,
    val userName: String,
    val timestamp: String,
    val avatarUrl: String,
    val type: StatusType
)

private val recentUpdates = listOf(
    Status(
        "1",
        "Jane Doe",
        "15 minutes ago",
        "https://i.pravatar.cc/100?u=jane",
        StatusType.RECENT
    ),
    Status(
        "2",
        "John Smith",
        "Today, 9:23 AM",
        "https://i.pravatar.cc/100?u=john",
        StatusType.RECENT
    )
)

private val viewedUpdates = listOf(
    Status(
        "3",
        "Emily Carter",
        "Yesterday, 8:00 PM",
        "https://i.pravatar.cc/100?u=emily",
        StatusType.VIEWED
    )
)

// --- THEME ---

private object AppColors {
    val Primary = Color(0xFF075E54)
    val Secondary = Color(0xFF128C7E)
    val Accent = Color(0xFF25D366)
    val Background = Color(0xFFF7F7F7)
    val TextPrimary = Color(0xFF1F1F1F)
    val TextSecondary = Color(0xFF667781)
    val Divider = Color(0xFFE9EDEF)
    val FabSecondaryBackground = Color(0xFFF0F2F5)
    val ViewedStatusBorder = Color(0xFFAAAAAA)
}

// --- CUSTOM ICONS ---

private object CustomIcons {
    val Chat: ImageVector
        get() {
            if (_chat != null) return _chat!!

            return _chat!!
        }
    private var _chat: ImageVector? = null

    val Status: ImageVector
        get() {
            if (_status != null) return _status!!

            return _status!!
        }
    private var _status: ImageVector? = null
}

// --- MAIN SCREEN COMPOSABLE ---

@Composable
fun StatusScreen() {
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { StatusTopAppBar() },
        bottomBar = { StatusBottomBar() },
        floatingActionButton = { StatusFabColumn() },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        StatusList(modifier = Modifier.padding(paddingValues))
    }
}

// --- UI COMPONENTS ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatusTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Status",
                // The original design uses Montserrat. For a self-contained component,
                // we use the system's semi-bold font. To use Montserrat, add the font
                // to your res/font folder and create a FontFamily.
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        },
        actions = {
            IconButton(onClick = { /* TODO: Handle more options click */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More options")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppColors.Primary,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

@Composable
private fun StatusList(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            MyStatusItem(modifier = Modifier.padding(top = 16.dp))
        }

        stickyHeader {
            SectionTitle("RECENT UPDATES")
        }

        items(recentUpdates, key = { it.id }) { status ->
            ContactStatusItem(status = status)
        }

        stickyHeader {
            SectionTitle("VIEWED UPDATES")
        }

        items(viewedUpdates, key = { it.id }) { status ->
            ContactStatusItem(status = status)
        }
    }
}

@Composable
private fun MyStatusItem(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { /* TODO: Handle add status click */ }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusAvatar(
            avatarUrl = "https://i.pravatar.cc/100?u=me",
            showAddIcon = true,
            contentDescription = "My status avatar"
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = "My status",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = AppColors.TextPrimary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Tap to add status update",
                fontSize = 14.sp,
                color = AppColors.TextSecondary
            )
        }
    }
}

@Composable
private fun ContactStatusItem(status: Status) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { /* TODO: Handle view status click */ }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusAvatar(
            avatarUrl = status.avatarUrl,
            hasStatus = status.type == StatusType.RECENT,
            isViewed = status.type == StatusType.VIEWED,
            contentDescription = "${status.userName}'s status avatar"
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = status.userName,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = AppColors.TextPrimary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = status.timestamp,
                fontSize = 14.sp,
                color = AppColors.TextSecondary
            )
        }
    }
}

@Composable
private fun StatusAvatar(
    avatarUrl: String,
    contentDescription: String,
    hasStatus: Boolean = false,
    isViewed: Boolean = false,
    showAddIcon: Boolean = false
) {
    val borderColor = when {
        hasStatus -> AppColors.Accent
        isViewed -> AppColors.ViewedStatusBorder
        else -> Color.White // As per original CSS for 'My Status'
    }

    Box(contentAlignment = Alignment.Center) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(54.dp)
                .border(2.dp, borderColor, CircleShape)
                .padding(if (hasStatus) 2.dp else 0.dp) // Padding inside the border for recent status
                .clip(CircleShape)
        )
        if (showAddIcon) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(22.dp)
                    .background(AppColors.Accent, CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add status",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = AppColors.TextSecondary,
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Background)
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .padding(top = 8.dp) // Simulates the 16px top padding of the section
    )
}

@Composable
private fun StatusFabColumn() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FloatingActionButton(
            onClick = { /* TODO: Handle text status click */ },
            shape = RoundedCornerShape(16.dp),
            containerColor = AppColors.FabSecondaryBackground,
            contentColor = AppColors.Primary,
            modifier = Modifier.size(48.dp),
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp)
        ) {
            Icon(Icons.Default.Edit, contentDescription = "New text status")
        }
        FloatingActionButton(
            onClick = { /* TODO: Handle camera status click */ },
            shape = RoundedCornerShape(16.dp),
            containerColor = AppColors.Accent,
            contentColor = Color.White,
            modifier = Modifier.size(56.dp),
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "New camera status",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun StatusBottomBar() {
    var selectedItem by remember { mutableStateOf(1) }
    val items = listOf("Chats", "Status", "Calls")
    val icons = listOf(Icons.Rounded.ChatBubble, Icons.Rounded.SignalWifiStatusbar4Bar, Icons.Default.Call)

    NavigationBar(
        containerColor = Color.White,
        contentColor = AppColors.TextSecondary,
        tonalElevation = 0.dp,
        modifier = Modifier.border(width = 1.dp, color = AppColors.Divider, shape = RectangleShape)
    ) {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = screen,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(screen, fontSize = 12.sp) },
                selected = selectedItem == index,
                onClick = { selectedItem = index },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColors.Secondary,
                    selectedTextColor = AppColors.Secondary,
                    unselectedIconColor = AppColors.TextSecondary,
                    unselectedTextColor = AppColors.TextSecondary,
                    indicatorColor = Color.White
                )
            )
        }
    }
}

// --- PREVIEW ---

@Preview(showBackground = true, widthDp = 375)
@Composable
fun StatusScreenPreview() {
    StatusScreen()
}