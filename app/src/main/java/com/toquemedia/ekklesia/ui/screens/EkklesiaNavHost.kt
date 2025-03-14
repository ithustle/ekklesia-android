package com.toquemedia.ekklesia.ui.screens

import com.toquemedia.ekklesia.model.BottomBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.ui.navigation.authNavigation
import com.toquemedia.ekklesia.ui.navigation.bibleNavigation
import com.toquemedia.ekklesia.ui.navigation.chapterNavigation
import com.toquemedia.ekklesia.ui.navigation.communityNavigation
import com.toquemedia.ekklesia.ui.navigation.verseNavigation
import com.toquemedia.ekklesia.ui.screens.login.LoginScreen

@Composable
fun EkklesiaNavHost(
    modifier: Modifier = Modifier,
    isLoginActive: Boolean,
    navController: NavHostController,
    showDevocionalModal: (@Composable () -> Unit) -> Unit,
    onClickLogin: () -> Unit,
    hideDevocionalModal: () -> Unit
) {
    NavHost(
        navController,
        startDestination = if (isLoginActive) BottomBarItem.Bible.route else BottomBarItem.Login.route,
        modifier = modifier
    ) {
        authNavigation(
            navController = navController,
            onClickLogin = onClickLogin
        )
        //homeNavigation()
        bibleNavigation(navController = navController)
        communityNavigation(navController = navController)
        chapterNavigation(navController = navController)
        verseNavigation(
            showDevocionalModal = showDevocionalModal,
            hideDevocionalModal = hideDevocionalModal
        )
    }
}

fun NavHostController.navigateBetweenTabs(destination: String) = this.navigate(destination) {
    popUpTo(this@navigateBetweenTabs.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}