import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.theme.PrincipalColor
import com.toquemedia.ekklesia.ui.theme.backgroundLightColor

sealed class BottomBarItem(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: String
) {
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
}

sealed class Screen(val route: String) {
    data object Chapters : Screen("chapters")
    data object Verses : Screen("verses")
}

val ekklesiaBottomBarItems = listOf(BottomBarItem.Home, BottomBarItem.Bible)

@Composable
fun EkklesiaBottomNavigation(
    barItems: List<BottomBarItem>,
    currentScreen: BottomBarItem,
    onTabSelected: (BottomBarItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = backgroundLightColor,
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
                    Text(stringResource(screen.label))
                },
            )
        }
    }
}