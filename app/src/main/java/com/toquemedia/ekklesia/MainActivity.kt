package com.toquemedia.ekklesia

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.model.EkklesiaBottomNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.toquemedia.ekklesia.ui.composables.EkklesiaModalSheet
import com.toquemedia.ekklesia.ui.navigation.navigateToBible
import com.toquemedia.ekklesia.ui.navigation.navigateToCommunity
import com.toquemedia.ekklesia.ui.navigation.navigateToHome
import com.toquemedia.ekklesia.ui.screens.EkklesiaNavHost
import com.toquemedia.ekklesia.ui.theme.EkklesiaTheme
import dagger.hilt.android.AndroidEntryPoint
import com.toquemedia.ekklesia.model.ekklesiaBottomBarItems
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EkklesiaTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    val navController = rememberNavController()
                    val bottomSheetState = rememberModalBottomSheetState(
                        initialValue = ModalBottomSheetValue.Hidden,
                        skipHalfExpanded = true
                    )
                    val sheetContent = remember { mutableStateOf<@Composable () -> Unit>({ }) }
                    val scope = rememberCoroutineScope()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    val currentRoute = currentDestination?.route
                    val selectedItem = remember(currentDestination) {
                        when (currentRoute) {
                            "home" -> BottomBarItem.Home
                            "bible" -> BottomBarItem.Bible
                            else -> BottomBarItem.Community
                        }
                    }

                    EkklesiaApp(
                        tabSelected = selectedItem,
                        sheetState = bottomSheetState,
                        sheetContent = sheetContent.value,
                        onTabItemChange = {
                            when(it.label) {
                                BottomBarItem.Home.label -> navController.navigateToHome()
                                BottomBarItem.Bible.label -> navController.navigateToBible()
                                BottomBarItem.Community.label -> navController.navigateToCommunity()
                            }
                        }
                    ) {
                        EkklesiaNavHost(
                            navController = navController,
                            showDevocionalModal = { content ->
                                sheetContent.value = content
                                scope.launch { bottomSheetState.show() }
                            },
                            hideDevocionalModal = {
                                scope.launch { bottomSheetState.hide() }
                            }
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
    sheetState: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    tabSelected: BottomBarItem = ekklesiaBottomBarItems.first(),
    onTabItemChange: (BottomBarItem) -> Unit,
    content: @Composable () -> Unit
) {
    EkklesiaModalSheet(
        sheetState = sheetState,
        sheetContent = sheetContent,
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
            Box(
                modifier = modifier
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                content()
            }
        }
    }
}