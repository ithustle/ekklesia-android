package com.toquemedia.ekklesia

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.toquemedia.ekklesia.routes.EkklesiaNavHost
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateToBibleGraph
import com.toquemedia.ekklesia.routes.navigateToCommunityGraph
import com.toquemedia.ekklesia.routes.navigateToHomeGraph
import com.toquemedia.ekklesia.ui.navigation.navigateToProfile
import com.toquemedia.ekklesia.ui.theme.EkklesiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkPermission()

        setContent {
            val navController = rememberNavController()
            val appViewModel = hiltViewModel<AppViewModel>()

            appViewModel.activityContext = this

            CompositionLocalProvider(LocalAppViewModel provides appViewModel) {
                EkklesiaTheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {

                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        val currentRoute = currentDestination?.route
                        val selectedItem by remember(currentDestination) {
                            val item = when(currentRoute) {
                                Screen.Home::class.qualifiedName -> Screen.Home
                                Screen.Bible::class.qualifiedName -> Screen.Bible
                                Screen.Communities::class.qualifiedName -> Screen.Communities
                                Screen.Profile::class.qualifiedName -> Screen.Profile
                                Screen.CreateCommunity::class.qualifiedName -> Screen.CreateCommunity
                                else -> Screen.CommunityFeed(communityId = "", communityName = "")
                            }
                            mutableStateOf(item)
                        }

                        EkklesiaApp(
                            tabSelected = selectedItem,
                            onTabItemChange = {
                                when(it) {
                                    Screen.Home -> navController.navigateToHomeGraph()
                                    Screen.Bible -> navController.navigateToBibleGraph()
                                    else -> navController.navigateToCommunityGraph()
                                }
                            },
                            currentUser = appViewModel.currentUser,
                            onNavigateToProfile = {
                                navController.navigateToProfile()
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            topBarState = appViewModel.topBarState.value,
                            content = {
                                EkklesiaNavHost(
                                    navController = navController,
                                    isLoginActive = appViewModel.currentUser != null
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    private fun checkPermission() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
    }
}