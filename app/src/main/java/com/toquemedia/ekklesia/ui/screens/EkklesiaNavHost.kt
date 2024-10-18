package com.toquemedia.ekklesia.ui.screens

import BottomBarItem
import Screen
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.ui.screens.bible.TestamentScreen
import com.toquemedia.ekklesia.ui.screens.bible.TestamentViewModel
import com.toquemedia.ekklesia.ui.screens.bible.chapter.ChapterScreen
import com.toquemedia.ekklesia.ui.screens.bible.verses.VerseViewModel
import com.toquemedia.ekklesia.ui.screens.bible.verses.VersesScreen
import com.toquemedia.ekklesia.ui.screens.home.HomeScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
            val viewModel: TestamentViewModel = hiltViewModel()
            val states by viewModel.uiState.collectAsState()

            TestamentScreen(
                states = states,
                onNavigateToBook = {
                    navController.navigate("${Screen.Chapters.route}/$it")
                }
            )
        }
        composable("${Screen.Chapters.route}/{bookName}") { backStackEntry ->

            val viewModel: TestamentViewModel = hiltViewModel()
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
            val vmTestament: TestamentViewModel = hiltViewModel()
            val vmVerses: VerseViewModel = hiltViewModel()

            val testamentStates by vmTestament.uiState.collectAsState()
            val versesStates by vmVerses.uiState.collectAsState()

            val chapterNumber = backStackEntry.arguments?.getString("chapterNumber")
            val bookName = backStackEntry.arguments?.getString("bookName")
            val scope = rememberCoroutineScope()
            val scrollState = rememberScrollState()
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

            bookName?.let {
                vmTestament.getVersesOfTheChapter(it)
            }

            VersesScreen(
                book = testamentStates.book,
                chapterNumber = chapterNumber,
                scrollState = scrollState,
                sheetState = sheetState,
                versesStates = versesStates,
                onSelectedVerse = { verse, versicle ->
                    versesStates.onSelectVerse(verse, versicle)
                },
                onUnMarkVerse = { verse, versicle ->
                    scope.launch {
                        versesStates.onUnMarkVerse(verse)
                        vmVerses.unMarkVerse(
                            bookName = bookName,
                            chapter = chapterNumber,
                            versicle = versicle.toString()
                        )
                    }
                },
                onFavoriteVerse = {
                    scope.launch {
                        versesStates.apply {
                            vmVerses.markVerse(
                                bookName,
                                chapterNumber,
                                versicle.toString(),
                                selectedVerse
                            )
                            onMarkVerse(selectedVerse)
                            onSelectVerse("", -1)
                            onShowVerseAction(false)
                        }
                    }
                },
                onNextVerse = { versicle ->
                    if (versicle < (chapterNumber?.toInt() ?: 0)) {
                        //chapter += 1
                        scope.launch {
                            scrollState.scrollTo(0)
                        }
                    }
                },
                onPreviousVerse = { versicle ->
                    if (versicle > 1) {
                        //chapter -= 1
                        scope.launch {
                            scrollState.scrollTo(0)
                        }
                    }
                },
                onDismissActionOptionVerse = {
                    versesStates.onSelectVerse("", -1)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            versesStates.onShowVerseAction(false)
                        }
                    }
                },
            )
        }
    }
}