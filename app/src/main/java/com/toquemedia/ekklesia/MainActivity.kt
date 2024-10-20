package com.toquemedia.ekklesia

import BottomBarItem
import EkklesiaBottomNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.toquemedia.ekklesia.ui.navigation.navigateToBible
import com.toquemedia.ekklesia.ui.navigation.navigateToHome
import com.toquemedia.ekklesia.ui.screens.EkklesiaNavHost
import com.toquemedia.ekklesia.ui.theme.EkklesiaTheme
import dagger.hilt.android.AndroidEntryPoint
import ekklesiaBottomBarItems

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EkklesiaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {

                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    val currentRoute = currentDestination?.route
                    val selectedItem = remember(currentDestination) {
                        when (currentRoute) {
                            "home" -> BottomBarItem.Home
                            else -> BottomBarItem.Bible
                        }
                    }

                    EkklesiaApp(
                        tabSelected = selectedItem,
                        onTabItemChange = {
                            when(it.label) {
                                BottomBarItem.Home.label -> navController.navigateToHome()
                                BottomBarItem.Bible.label -> navController.navigateToBible()
                            }
                        }
                    ) {
                        EkklesiaNavHost(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EkklesiaApp(
    modifier: Modifier = Modifier,
    tabSelected: BottomBarItem = ekklesiaBottomBarItems.first(),
    onTabItemChange: (BottomBarItem) -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            EkklesiaBottomNavigation(
                barItems = ekklesiaBottomBarItems,
                currentScreen = tabSelected,
                onTabSelected = onTabItemChange
            )
        }
    ) { padding ->
        Box(modifier = modifier.padding(padding)) {
            content()
        }
    }

}