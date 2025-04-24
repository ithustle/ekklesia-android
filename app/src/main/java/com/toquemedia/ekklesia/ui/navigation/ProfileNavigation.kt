package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.model.TopBarState
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateBetweenScreens
import com.toquemedia.ekklesia.ui.screens.bible.worship.CreateWorshipScreen
import com.toquemedia.ekklesia.ui.screens.bible.worship.WorshipViewModel
import com.toquemedia.ekklesia.ui.screens.login.AuthViewModel
import com.toquemedia.ekklesia.ui.screens.profile.MyWorshipScreen
import com.toquemedia.ekklesia.ui.screens.profile.ProfileScreen

fun NavGraphBuilder.profileNavigation(navController: NavController) {
    composable<Screen.Profile> {

        val viewModel = hiltViewModel<AuthViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val appViewModel = LocalAppViewModel.current

        LaunchedEffect(Unit) {
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = "Perfil",
                    showBackButton = true,
                    isBackgroundTransparent = true,
                    onBackNavigation = {
                        navController.popBackStack()
                    }
                )
            )
        }

        uiState.user?.let {
            ProfileScreen(
                user = it,
                onSignOut = {
                    appViewModel.logout()
                },
                onNavigateToMyDevocional = {
                    navController.navigateToMyWorships()
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

        val vmWorship: WorshipViewModel = hiltViewModel(bibleGraphEntry)
        val worshipState by vmWorship.uiState.collectAsStateWithLifecycle()

        val appViewModel = LocalAppViewModel.current

        LaunchedEffect(Unit) {
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
            onShareWorship = {

            },
            onEditWorship = {
                vmWorship.prepareWorshipForEdit(it)
                navController.navigateToEditWorship()
            },
            onDeleteWorship = {
                vmWorship.deleteWorship(it.id)
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
}

fun NavController.navigateToProfile() = this.navigateBetweenScreens(Screen.Profile)
fun NavController.navigateToMyWorships() = this.navigateBetweenScreens(Screen.MyWorships)
fun NavController.navigateToEditWorship() = this.navigateBetweenScreens(Screen.EditWorship)