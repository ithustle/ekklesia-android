package com.toquemedia.ekklesia.model

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

sealed class BottomBarItem(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: String
) {
    data object Login : BottomBarItem(
        label = R.string.login,
        icon = Icons.Rounded.Dashboard,
        route = "home"
    )
    data object Home : BottomBarItem(
        label = R.string.home,
        icon = Icons.Rounded.Dashboard,
        route = "home"
    )

    data object Bible : BottomBarItem(
        label = R.string.bible,
        icon = Icons.Rounded.Book,
        route = "bible"
    )

    data object Community : BottomBarItem(
        label = R.string.community,
        icon = Icons.Rounded.Groups,
        route = "community"
    )
}

sealed class Screen(val route: String) {
    data object Chapters : Screen("chapters")
    data object Verses : Screen("verses")
    data object CreateCommunity: Screen("createCommunity")
}

val ekklesiaBottomBarItems = listOf(BottomBarItem.Bible, BottomBarItem.Community)

@Composable
fun EkklesiaBottomNavigation(
    barItems: List<BottomBarItem>,
    currentScreen: BottomBarItem,
    onTabSelected: (BottomBarItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
    ) {
        barItems.forEach { screen ->
            BottomNavigationItem(
                onClick = {
                    onTabSelected(screen)
                },
                selected = currentScreen.label == screen.label,
                selectedContentColor = if (currentScreen.label == screen.label) PrincipalColor else Color.Gray,
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = null,
                        tint = if (currentScreen.label == screen.label) PrincipalColor else Color.Gray
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