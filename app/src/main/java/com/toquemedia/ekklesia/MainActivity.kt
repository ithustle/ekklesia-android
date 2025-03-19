package com.toquemedia.ekklesia

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.Manifest
import android.content.pm.PackageManager
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.model.EkklesiaBottomNavigation
import com.toquemedia.ekklesia.model.ekklesiaBottomBarItems
import com.toquemedia.ekklesia.routes.EkklesiaNavHost
import com.toquemedia.ekklesia.ui.composables.EkklesiaModalSheet
import com.toquemedia.ekklesia.ui.navigation.navigateToBible
import com.toquemedia.ekklesia.ui.navigation.navigateToCommunity
import com.toquemedia.ekklesia.ui.theme.EkklesiaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkPermission()

        setContent {
            val navController = rememberNavController()

            EkklesiaTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {

                    val bottomSheetState = rememberModalBottomSheetState(
                        initialValue = ModalBottomSheetValue.Hidden,
                        skipHalfExpanded = true
                    )
                    val sheetContent = remember { mutableStateOf<@Composable () -> Unit>({ }) }
                    val scope = rememberCoroutineScope()

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    val currentRoute = currentDestination?.route
                    val selectedItem by remember(currentDestination) {
                        val item = when (currentRoute) {
                            "home" -> BottomBarItem.Home
                            "bible" -> BottomBarItem.Bible
                            else -> BottomBarItem.Community
                        }
                        mutableStateOf(item)
                    }

                    val containsInBottomAppBarItems = when(currentRoute) {
                        BottomBarItem.Login.route -> false
                        else -> true
                    }

                    EkklesiaApp(
                        tabSelected = selectedItem,
                        sheetState = bottomSheetState,
                        sheetContent = sheetContent.value,
                        onTabItemChange = {
                            when(it.label) {
                                BottomBarItem.Bible.label -> navController.navigateToBible()
                                BottomBarItem.Community.label -> navController.navigateToCommunity()
                            }
                        },
                        isLoginActive = containsInBottomAppBarItems
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

    private fun checkPermission() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
    }
}

@Composable
fun EkklesiaApp(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    tabSelected: BottomBarItem = ekklesiaBottomBarItems.first(),
    onTabItemChange: (BottomBarItem) -> Unit,
    isLoginActive: Boolean,
    content: @Composable () -> Unit,
) {
    EkklesiaModalSheet(
        sheetState = sheetState,
        sheetContent = sheetContent,
    ) {
        Scaffold(
            bottomBar = {
                if (isLoginActive) {
                    EkklesiaBottomNavigation(
                        barItems = ekklesiaBottomBarItems,
                        currentScreen = tabSelected,
                        onTabSelected = onTabItemChange
                    )
                }
            }
        ) { padding ->
            Box(
                modifier = modifier
                    .padding(padding)
            ) {
                content()
            }
        }
    }
}