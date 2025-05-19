package com.toquemedia.seedfy

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.toquemedia.seedfy.model.FcmManager
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.routes.EkklesiaNavHost
import com.toquemedia.seedfy.routes.Screen
import com.toquemedia.seedfy.routes.navigateToBibleGraph
import com.toquemedia.seedfy.routes.navigateToCommunityGraph
import com.toquemedia.seedfy.routes.navigateToFirstScreen
import com.toquemedia.seedfy.routes.navigateToHomeGraph
import com.toquemedia.seedfy.ui.navigation.navigateToAddCommentOnPost
import com.toquemedia.seedfy.ui.navigation.navigateToCommunityFeed
import com.toquemedia.seedfy.ui.navigation.navigateToProfile
import com.toquemedia.seedfy.ui.screens.community.CommunityViewModel
import com.toquemedia.seedfy.ui.screens.community.feed.FeedCommunityViewModel
import com.toquemedia.seedfy.ui.theme.EkklesiaTheme
import com.toquemedia.seedfy.utils.AlarmScheduler
import com.toquemedia.seedfy.utils.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.jvm.java

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val communityViewModel: CommunityViewModel by viewModels()
    private val communityFeedViewModel: FeedCommunityViewModel by viewModels()
    private val appViewModel: AppViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        checkPermission()
        requestNotificationPermissionIfNeeded()

        NotificationHelper.createNotificationChannel(this)
        checkExactAlarmPermission()

        lifecycleScope.launch {
            if (appViewModel.currentUser.value != null) {
                communityViewModel.uiState.collect { state ->
                    splashScreen.setKeepOnScreenCondition {
                        state.loadingCommunitiesUserIn
                    }
                }
            }
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            val fcmManager = remember { FcmManager(this) }

            val currentUser by appViewModel.currentUser.collectAsStateWithLifecycle()
            val isLoginActive = currentUser != null

            appViewModel.activityContext = this

            LaunchedEffect(currentUser) {
                if (currentUser == null) {
                    navController.navigateToFirstScreen(Screen.AuthScreenGraph)
                } else {
                    navController.navigateToFirstScreen(Screen.HomeScreenGraph)
                }
            }

            LaunchedEffect(Unit) {
                communityViewModel.uiState
                    .filter { it.loadingCommunitiesUserIn == false }
                    .collectLatest { state ->
                        val postId = intent.getStringExtra("postId")
                        val communityId = intent.getStringExtra("communityId")
                        val postString = intent.getStringExtra("post")

                        if (postId != null && communityId != null) {
                            val selected = state.communitiesUserIn.firstOrNull { it.community.id == communityId }

                            if (selected != null) {
                                val post = Gson().fromJson(postString, PostType::class.java)
                                communityFeedViewModel.getAllPosts(communityId)
                                communityFeedViewModel.selectPost(post)
                                communityViewModel.selectCommunity(selected)

                                navController.navigateToCommunityFeed()

                                val lifecycle = navController.getBackStackEntry(Screen.CommunityFeed).lifecycle
                                snapshotFlow { lifecycle.currentState }
                                    .filter { it == Lifecycle.State.RESUMED }
                                    .first()

                                navController.navigateToAddCommentOnPost(postId, communityId)
                            }

                            withContext(Dispatchers.Main) {
                                intent.removeExtra("postId")
                                intent.removeExtra("communityId")
                            }
                        }
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
                            val item = when (currentRoute) {
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
                                when (it) {
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
                            fcmManager = fcmManager,
                            topBarState = appViewModel.topBarState.value,
                            showTopBar = appViewModel.showTopBar,
                            videoPlayerVisible = appViewModel.videoPlayerVisible,
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

    private fun checkExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).also {
                    startActivity(it)
                }
            } else {
                AlarmScheduler.scheduleDailyAlarm(this)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appViewModel.bible = emptyList()
        appViewModel.books = emptyList()
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {

                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    Toast.makeText(
                        this,
                        "Precisamos da permissão para enviar notificações diárias do versículo do dia",
                        Toast.LENGTH_LONG
                    ).show()
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Notificações permitidas", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                this,
                "As notificações diárias não funcionarão sem permissão",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun checkPermission() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ),
                1001
            )
    }
}