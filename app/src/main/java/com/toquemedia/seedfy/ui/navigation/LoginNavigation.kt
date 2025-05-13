package com.toquemedia.seedfy.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.seedfy.LocalAppViewModel
import com.toquemedia.seedfy.routes.Screen
import com.toquemedia.seedfy.routes.navigateBetweenScreens
import com.toquemedia.seedfy.routes.navigateToFirstScreen
import com.toquemedia.seedfy.ui.screens.login.AuthViewModel
import com.toquemedia.seedfy.ui.screens.login.LoginScreen
import com.toquemedia.seedfy.ui.screens.onBoarding.OnboardingPageContent
import com.toquemedia.seedfy.ui.screens.onBoarding.onboardingPages

fun NavGraphBuilder.loginNavigation(navController: NavController) {
    composable<Screen.Login> {

        val viewModel = hiltViewModel<AuthViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val appViewModel = LocalAppViewModel.current

        LoginScreen(
            state = uiState,
            onClickLogin = {
                viewModel.signIn(appViewModel.activityContext)
            },
            onUserAuthenticated = {
                appViewModel.setCurrentUser(it)
            }
        )
    }
    composable<Screen.FirstPageOnboarding> {
        OnboardingPageContent(
            page = onboardingPages[0],
            isLast = false,
            onNext = { navController.navigateToSecondOnboarding() },
            onSkip = { navController.navigateToLogin() }
        )
    }
    composable<Screen.SecondPageOnboarding> {

        OnboardingPageContent(
            page = onboardingPages[1],
            isLast = false,
            onNext = {
                navController.navigateToThirdOnboarding()
            },
            onSkip = { navController.navigateToLogin() }
        )
    }
    composable<Screen.ThirdPageOnboarding> {


        OnboardingPageContent(
            page = onboardingPages[2],
            isLast = true,
            onNext = {
                navController.navigateToLogin()
            },
            onSkip = { navController.navigateToLogin() }
        )
    }
}

fun NavController.navigateToLogin() = this.navigateToFirstScreen(Screen.Login)

fun NavController.navigateToSecondOnboarding() =
    this.navigateBetweenScreens(Screen.SecondPageOnboarding)

fun NavController.navigateToThirdOnboarding() =
    this.navigateBetweenScreens(Screen.ThirdPageOnboarding)