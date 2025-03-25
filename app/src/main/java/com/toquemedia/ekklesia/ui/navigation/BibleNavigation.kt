package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.routes.navigateBetweenTabs
import com.toquemedia.ekklesia.ui.screens.bible.TestamentScreen
import com.toquemedia.ekklesia.ui.screens.bible.TestamentViewModel
import com.toquemedia.ekklesia.ui.screens.login.AuthViewModel

fun NavGraphBuilder.bibleNavigation(navController: NavController) {
    composable(BottomBarItem.Bible.route) {

        val viewModelAuth = hiltViewModel<AuthViewModel>()
        val uiAuthState by viewModelAuth.uiState.collectAsState()

        uiAuthState.user?.let {
            val viewModel: TestamentViewModel = hiltViewModel()
            val states by viewModel.uiState.collectAsState()

            TestamentScreen(
                states = states,
                onNavigateToChapter = {
                    navController.navigateToChapter(bookName = it)
                }
            )
        } ?: LaunchedEffect(null) {
            navController.navigateToLogin()
        }
    }
}