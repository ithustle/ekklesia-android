package com.toquemedia.ekklesia.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.ui.navigation.loginNavigation

internal const val authGraphRoute = "authGraph"

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = BottomBarItem.Home.route, route = authGraphRoute) {
        loginNavigation(navController = navController)
    }
}