package com.toquemedia.ekklesia.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateBetweenScreens
import com.toquemedia.ekklesia.routes.navigateToFirstScreen
import com.toquemedia.ekklesia.ui.screens.login.AuthViewModel
import com.toquemedia.ekklesia.ui.screens.login.LoginScreen
import com.toquemedia.ekklesia.ui.screens.login.PhoneAuthScreen
import com.toquemedia.ekklesia.ui.screens.onBoarding.OnboardingPageContent
import com.toquemedia.ekklesia.ui.screens.onBoarding.onboardingPages

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
            onClickPhoneLogin = {
                navController.navigateToPhoneAuth()
            },
            onUserAuthenticated = {
                appViewModel.setCurrentUser(it)
            }
        )
    }

    composable<Screen.PhoneAuth> {
        val viewModel = hiltViewModel<AuthViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val appViewModel = LocalAppViewModel.current

        LaunchedEffect(uiState.user) {
            if (uiState.user != null) {
                appViewModel.setCurrentUser(uiState.user)
            }
        }

        PhoneAuthScreen(
            state = uiState,
            onSendCode = { phoneNumber ->
                appViewModel.activityContext?.let { activity ->
                    viewModel.sendPhoneVerificationCode(phoneNumber, activity)
                }
            },
            onVerifyCode = { code ->
                viewModel.verifyPhoneCode(code)
            },
            onCompleteProfile = { firstName, lastName ->
                viewModel.completeProfile(firstName, lastName)
            },
            onBackToLogin = {
                viewModel.resetPhoneAuth()
                navController.popBackStack()
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

fun NavController.navigateToPhoneAuth() = this.navigateBetweenScreens(Screen.PhoneAuth)

fun NavController.navigateToSecondOnboarding() =
    this.navigateBetweenScreens(Screen.SecondPageOnboarding)

fun NavController.navigateToThirdOnboarding() =
    this.navigateBetweenScreens(Screen.ThirdPageOnboarding)