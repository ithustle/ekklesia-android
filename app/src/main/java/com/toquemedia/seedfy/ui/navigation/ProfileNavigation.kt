package com.toquemedia.seedfy.ui.navigation

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.seedfy.LocalAppViewModel
import com.toquemedia.seedfy.model.TopBarState
import com.toquemedia.seedfy.routes.Screen
import com.toquemedia.seedfy.routes.navigateBetweenScreens
import com.toquemedia.seedfy.ui.composables.EkklesiaGenerating
import com.toquemedia.seedfy.ui.screens.bible.worship.CreateWorshipScreen
import com.toquemedia.seedfy.ui.screens.bible.worship.WorshipViewModel
import com.toquemedia.seedfy.ui.screens.biblePlan.BiblePlanListScreen
import com.toquemedia.seedfy.ui.screens.biblePlan.BiblePlanScreen
import com.toquemedia.seedfy.ui.screens.biblePlan.BiblePlanViewModel
import com.toquemedia.seedfy.ui.screens.community.CommunityViewModel
import com.toquemedia.seedfy.ui.screens.login.AuthViewModel
import com.toquemedia.seedfy.ui.screens.profile.MyWorshipScreen
import com.toquemedia.seedfy.ui.screens.profile.ProfileScreen
import kotlinx.coroutines.launch

fun NavGraphBuilder.profileNavigation(navController: NavController) {
    composable<Screen.Profile> {

        val viewModel = hiltViewModel<AuthViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val appViewModel = LocalAppViewModel.current

        LaunchedEffect(Unit) {
            appViewModel.showTopBar = false
        }

        uiState.user?.let {
            ProfileScreen(
                user = it,
                myNote = uiState.myNote,
                onChangeMyNote = uiState.onMyNote,
                onSaveNote = {
                    viewModel.saveMyNote(it)
                },
                onSignOut = {
                    appViewModel.logout()
                },
                onNavigateToMyDevocional = {
                    navController.navigateToMyWorships()
                },
                onNavigateToMyBiblePlans = {
                    navController.navigateToBiblePlansList()
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }

    composable<Screen.MyWorships> { backEntry ->

        val bibleGraphEntry = remember(navController.currentBackStackEntry) {
            try {
                if (navController.currentBackStackEntry?.destination?.route?.contains("BibleScreenGraph") == true) {
                    navController.getBackStackEntry(Screen.BibleScreenGraph)
                } else {
                    backEntry
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
                backEntry
            }
        }

        val context = LocalContext.current
        val appViewModel = LocalAppViewModel.current
        val activity = appViewModel.activityContext as ComponentActivity
        val vmCommunities = hiltViewModel<CommunityViewModel>(activity)
        val communitiesStates = vmCommunities.uiState.collectAsStateWithLifecycle()

        val vmWorship: WorshipViewModel = hiltViewModel(bibleGraphEntry)
        val worshipState by vmWorship.uiState.collectAsStateWithLifecycle()

        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            appViewModel.showTopBar = true
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = "Meus devocionais",
                    showBackButton = true,
                    isBackgroundTransparent = true,
                    onBackNavigation = {
                        navController.popBackStack()
                    }
                )
            )
        }

        MyWorshipScreen(
            worships = worshipState.worships,
            communities = communitiesStates.value.myCommunities,
            sharingWorship = worshipState.sharingWorship,
            onShareToCommunityWorship = { communityId, worship ->
                scope.launch {
                    vmWorship.shareWorshipToCommunity(communityId, worship)
                    Toast.makeText(context, "Devocional partilhado com sucesso!", Toast.LENGTH_SHORT).show()
                }
            },
            onEditWorship = {
                vmWorship.prepareWorshipForEdit(it)
                navController.navigateToEditWorship()
            },
            onDeleteWorship = {
                vmWorship.deleteWorship(it.id)
            },
            onDialogOpened = {
                appViewModel.showBackgroundOverlay = true
            },
            onDismissDialog = {
                appViewModel.showBackgroundOverlay = false
            }
        )
    }

    composable<Screen.EditWorship> { backStack ->

        val vmWorship: WorshipViewModel = hiltViewModel(navController.previousBackStackEntry!!)
        val worshipState by vmWorship.uiState.collectAsStateWithLifecycle()

        val appViewModel = LocalAppViewModel.current
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = "Editar devocional",
                    showBackButton = true,
                    isBackgroundTransparent = true,
                    onBackNavigation = {
                        navController.popBackStack()
                    }
                )
            )
        }

        CreateWorshipScreen(
            context = context,
            state = worshipState,
            isUpdating = true,
            onUpdateWorship = {
                vmWorship.updateWorship(it)
                navController.popBackStack()
            },
            onDeleteWorshipVideo = {
                vmWorship.deleteRecordedVideo()
            }
        )
    }

    composable<Screen.BiblePlan> {

        navController.previousBackStackEntry?.let { navEntry ->
            val viewModel = hiltViewModel<BiblePlanViewModel>(navEntry)
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            val appViewModel = LocalAppViewModel.current

            LaunchedEffect(Unit) {
                appViewModel.showTopBar = false
            }

            state.biblePlan?.let {
                BiblePlanScreen(
                    biblePlan = it,
                    onCheckAsRead = { checked, day ->
                        viewModel.updateBiblePlan(it, checked, day)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            } ?: run {
                EkklesiaGenerating(label = "A carregar o plano...")
            }
        }
    }

    composable<Screen.BiblePlansList> {
        val viewModel = hiltViewModel<BiblePlanViewModel>(it)
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        val appViewModel = LocalAppViewModel.current

        LaunchedEffect(Unit) {
            appViewModel.showTopBar = true
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = "Planos de leitura",
                    useAvatar = null,
                    showTitleAvatar = false,
                    showBackButton = true,
                    onBackNavigation = { navController.popBackStack() }
                )
            )
        }

        if (state.loadingPlans) {
            EkklesiaGenerating(label = "A carregar o plano...")
        } else {
            BiblePlanListScreen(
                plans = state.biblePlans,
                onNavigateToBiblePlan = {
                    viewModel.getBiblePlan(it.title)
                    navController.navigateToBiblePlan()
                }
            )
        }
    }
}

fun NavController.navigateToProfile() = this.navigateBetweenScreens(Screen.Profile)
fun NavController.navigateToMyWorships() = this.navigateBetweenScreens(Screen.MyWorships)
fun NavController.navigateToEditWorship() = this.navigateBetweenScreens(Screen.EditWorship)
fun NavController.navigateToBiblePlansList() = this.navigateBetweenScreens(Screen.BiblePlansList)
fun NavController.navigateToBiblePlan() = this.navigateBetweenScreens(Screen.BiblePlan)