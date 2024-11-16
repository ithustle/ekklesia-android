package com.toquemedia.ekklesia.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.model.BottomBarItem
import com.toquemedia.ekklesia.ui.screens.community.CommunityListScreen
import com.toquemedia.ekklesia.ui.screens.navigateBetweenTabs

fun NavGraphBuilder.communityNavigation(navController: NavHostController) {
    composable(BottomBarItem.Community.route) {
        CommunityListScreen()
    }
}

fun NavHostController.navigateToCommunity() {
    this.navigateBetweenTabs(BottomBarItem.Community.route)
}