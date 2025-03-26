package com.toquemedia.ekklesia.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.toquemedia.ekklesia.ui.navigation.loginNavigation

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<Screen.AuthScreenGraph>(startDestination = Screen.Login) {
        loginNavigation(navController = navController)
    }
}