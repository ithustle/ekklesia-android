package com.toquemedia.ekklesia.ui.screens

import Screen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.ui.screens.bible.TestamentScreen
import com.toquemedia.ekklesia.ui.screens.bible.TestamentViewModel
import com.toquemedia.ekklesia.ui.screens.bible.TestamentViewModelFactory
import com.toquemedia.ekklesia.ui.screens.bible.chapter.ChapterScreen
import com.toquemedia.ekklesia.ui.screens.bible.verses.VersesScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeScreen

@Composable
fun EkklesiaNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController,
        startDestination = BottomBarItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomBarItem.Home.route) {
            HomeScreen()
        }
        composable(BottomBarItem.Bible.route) {
            val viewModel: TestamentViewModel = viewModel(
                factory = TestamentViewModelFactory(LocalContext.current)
            )
            val states by viewModel.uiState.collectAsState()

            TestamentScreen(
                states = states,
                onNavigateToBook = {
                    navController.navigate("${Screen.Chapters.route}/$it")
                }
            )
        }
        composable("${Screen.Chapters.route}/{bookName}") { backStackEntry ->

            val viewModel: TestamentViewModel = viewModel(
                factory = TestamentViewModelFactory(LocalContext.current)
            )
            val states by viewModel.uiState.collectAsState()
                val bookName = backStackEntry.arguments?.getString("bookName")

            bookName?.let {
                viewModel.getChaptersOfTheBook(it)
            }

            ChapterScreen(
                states = states,
                onNavigateToVerses = {
                    navController.navigate("${Screen.Verses.route}/$bookName/$it")
                }
            )
        }
        composable("${Screen.Verses.route}/{bookName}/{chapterNumber}") { backStackEntry ->
            val viewModel: TestamentViewModel = viewModel(
                factory = TestamentViewModelFactory(LocalContext.current)
            )
            val states by viewModel.uiState.collectAsState()
            val chapterNumber = backStackEntry.arguments?.getString("chapterNumber")
            val bookName = backStackEntry.arguments?.getString("bookName")

            bookName?.let {
                viewModel.getVersesOfTheChapter(it)
            }

            VersesScreen(
                states = states,
                chapterNumber = chapterNumber
            )
        }
    }
}