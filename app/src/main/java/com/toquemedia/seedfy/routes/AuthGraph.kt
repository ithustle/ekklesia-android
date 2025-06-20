package com.toquemedia.seedfy.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.toquemedia.seedfy.ui.navigation.loginNavigation

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<Screen.AuthScreenGraph>(startDestination = Screen.FirstPageOnboarding) {
        loginNavigation(navController)
    }
}