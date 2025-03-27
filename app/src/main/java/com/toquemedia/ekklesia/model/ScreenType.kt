package com.toquemedia.ekklesia.model

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

sealed class BottomBarItem(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: Screen
) {
    data object Home : BottomBarItem(
        label = R.string.home,
        icon = Icons.Rounded.Home,
        route = Screen.Home
    )

    data object Bible : BottomBarItem(
        label = R.string.bible,
        icon = Icons.Rounded.Book,
        route = Screen.Bible
    )

    data object Community : BottomBarItem(
        label = R.string.community,
        icon = Icons.Rounded.Groups,
        route = Screen.Communities
    )
}

val ekklesiaBottomBarItems = listOf(
    BottomBarItem.Home,
    BottomBarItem.Bible,
    BottomBarItem.Community
)

@Composable
fun EkklesiaBottomNavigation(
    currentScreen: Screen,
    onTabSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
    ) {
        ekklesiaBottomBarItems.forEach { screen ->
            BottomNavigationItem(
                onClick = {
                    if (currentScreen != screen.route) {
                        onTabSelected(screen.route)
                    }
                },
                selected = currentScreen == screen.route,
                selectedContentColor = if (currentScreen == screen.route) PrincipalColor else Color.Gray,
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = null,
                        tint = if (currentScreen == screen.route) PrincipalColor else Color.Gray
                    )
                },
                label = {
                    Text(
                        stringResource(screen.label),
                        fontSize = 12.sp
                    )
                },
            )
        }
    }
}