package com.toquemedia.ekklesia

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.toquemedia.ekklesia.routes.EkklesiaNavHost
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateToBibleGraph
import com.toquemedia.ekklesia.routes.navigateToCommunityGraph
import com.toquemedia.ekklesia.routes.navigateToFirstScreen
import com.toquemedia.ekklesia.routes.navigateToHomeGraph
import com.toquemedia.ekklesia.ui.navigation.navigateToProfile
import com.toquemedia.ekklesia.ui.screens.community.CommunityViewModel
import com.toquemedia.ekklesia.ui.theme.EkklesiaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val communityViewModel: CommunityViewModel by viewModels()
    private val appViewModel: AppViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        checkPermission()

        lifecycleScope.launch {
            if (appViewModel.currentUser.value != null) {
                communityViewModel.uiState.collect { state ->
                    splashScreen.setKeepOnScreenCondition {
                        state.loadingCommunitiesUserIn
                    }
                }
            }
        }

        setContent {
            val navController = rememberNavController()

            val currentUser by appViewModel.currentUser.collectAsStateWithLifecycle()
            val isLoginActive = currentUser != null

            appViewModel.activityContext = this

            LaunchedEffect(currentUser) {
                println("AQUI.... $currentUser")
                if (currentUser == null) {
                    navController.navigateToFirstScreen(Screen.AuthScreenGraph)
                } else {
                    navController.navigateToFirstScreen(Screen.HomeScreenGraph)
                }
            }

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
                                else -> Screen.CommunityFeed
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
                            showOverlay = appViewModel.showBackgroundOverlay,
                            currentUser = currentUser,
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
                                    isLoginActive = isLoginActive
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appViewModel.bible = emptyList()
        appViewModel.books = emptyList()
    }

    private fun checkPermission() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                1001
            )
    }
}