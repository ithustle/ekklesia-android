package com.toquemedia.ekklesia.ui.navigation

import com.toquemedia.ekklesia.model.BottomBarItem
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.ui.screens.home.HomeScreen
import com.toquemedia.ekklesia.ui.screens.navigateBetweenTabs

fun NavGraphBuilder.homeNavigation() {
    composable(BottomBarItem.Home.route) {
        HomeScreen()
    }
}

fun NavHostController.navigateToHome() {
    this.navigateBetweenTabs(BottomBarItem.Home.route)
}