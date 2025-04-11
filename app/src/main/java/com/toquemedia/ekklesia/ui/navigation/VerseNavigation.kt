package com.toquemedia.ekklesia.ui.navigation

import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.model.TopBarState
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateBetweenScreens
import com.toquemedia.ekklesia.ui.screens.bible.devocional.CreateDevocionalScreen
import com.toquemedia.ekklesia.ui.screens.bible.devocional.DevocionalViewModel
import com.toquemedia.ekklesia.ui.screens.bible.notes.NoteScreen
import com.toquemedia.ekklesia.ui.screens.bible.verses.VerseActionOption
import com.toquemedia.ekklesia.ui.screens.bible.verses.VerseViewModel
import com.toquemedia.ekklesia.ui.screens.bible.verses.VersesScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.verseNavigation(
    navController: NavController
) {
    composable<Screen.Verses> { backStackEntry ->
        val vmVerses: VerseViewModel = hiltViewModel()
        val vmDevocional: DevocionalViewModel = hiltViewModel()

        val versesStates by vmVerses.uiState.collectAsState()
        val devocionalStates by vmDevocional.uiState.collectAsState()

        val args = backStackEntry.toRoute<Screen.Verses>()

        val chapterNumber = args.chapterNumber
        val bookName = args.bookName

        val scope = rememberCoroutineScope()
        val scrollState = rememberScrollState()

        val appViewModel = LocalAppViewModel.current

        val book = appViewModel.books.find { it.bookName == bookName }

        LaunchedEffect(Unit) {
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = "Capítulo $chapterNumber",
                    showBackButton = true,
                    onBackNavigation = {
                        navController.popBackStack()
                    }
                )
            )
        }

        if (versesStates.showVerseActionOption) {
            VerseActionOption(
                hasNote = versesStates.notes.find { it.verse == versesStates.selectedVerse } != null,
                hasDevocional = devocionalStates.allDevocional.find { it.verse == versesStates.selectedVerse && !it.draft } != null,
                onDismissRequest = { sheetState ->
                    versesStates.onSelectVerse("", -1)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            versesStates.onShowVerseAction(false)
                        }
                    }
                },
                onFavoriteVerse = {
                    vmVerses.markVerse(
                        bookName,
                        chapterNumber,
                        versesStates.versicle.toString(),
                        versesStates.selectedVerse
                    )
                },
                onAddNoteToVerse = {
                    navController.navigateToNoteVerse(
                        bookName,
                        chapterNumber,
                        versesStates.selectedVerse,
                        versesStates.versicle
                    )
                    versesStates.apply {
                        onShowVerseAction(false)
                        onSelectVerse("", -1)
                    }
                },
                onSelectVerseForDevocional = {
                    navController.navigateToCreateDevocional(
                        bookName,
                        chapterNumber,
                        versesStates.versicle,
                        versesStates.selectedVerse
                    )
                    versesStates.apply {
                        onShowVerseAction(false)
                        onSelectVerse("", -1)
                    }
                }
            )
        }

        VersesScreen(
            book = book,
            chapterNumber = chapterNumber,
            scrollState = scrollState,
            versesStates = versesStates,
            devocionalState = devocionalStates,
            onSelectedVerse = { verse, versicle ->
                versesStates.onSelectVerse(verse, versicle)
            },
            onUnMarkVerse = { verse, versicle ->
                vmVerses.unMarkVerse(
                    bookName = bookName,
                    chapter = chapterNumber,
                    versicle = versicle.toString(),
                    verse = verse
                )
            },
            onNextVerse = { versicle ->
                if (versicle < chapterNumber.toInt()) {
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
        )
    }

    composable<Screen.NoteVerse> { backStackEntry ->

        navController.previousBackStackEntry?.let {
            val appViewModel = LocalAppViewModel.current

            val vmVerses: VerseViewModel = hiltViewModel(it)

            val versesStates by vmVerses.uiState.collectAsState()

            val args = backStackEntry.toRoute<Screen.NoteVerse>()

            val chapterNumber = args.chapterNumber
            val bookName = args.bookName
            val versicle = args.versicle
            val selectedVerse = args.verse

            val context = LocalContext.current

            LaunchedEffect(Unit) {
                appViewModel.updateTopBarState(
                    newState = TopBarState(
                        title = "Adicionar nota",
                        showBackButton = true,
                        onBackNavigation = {
                            navController.popBackStack()
                        }
                    )
                )
            }

            NoteScreen(
                verse = selectedVerse,
                bookName = bookName.toString(),
                versicle = versicle,
                chapter = chapterNumber.toString(),
                entryNote = versesStates.entryNote,
                onEntryNoteChange = versesStates.onEntryNoteChange,
                savingNote = versesStates.savingNote,
                onSaveNote = {
                    vmVerses.addNoteToVerse(
                        bookName = bookName.toString(),
                        chapter = chapterNumber.toInt(),
                        versicle = versesStates.versicle,
                        verse = versesStates.selectedVerse
                    )
                    Toast.makeText(context, "Nota adicionada com sucesso", Toast.LENGTH_SHORT).show()
                },
                onSaveAndShareNote = {
                    vmVerses.saveAndShareNote(
                        bookName = bookName.toString(),
                        chapter = chapterNumber.toInt(),
                        versicle = versesStates.versicle,
                        verse = versesStates.selectedVerse
                    )
                    Toast.makeText(context, "Nota salva e partilhada com sucesso", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    composable<Screen.CreateDevocional> { backStackEntry ->

        val vmDevocional: DevocionalViewModel = hiltViewModel()
        val devocionalStates by vmDevocional.uiState.collectAsState()

        val args = backStackEntry.toRoute<Screen.CreateDevocional>()

        val chapterNumber = args.chapterNumber
        val bookName = args.bookName
        val versicle = args.versicle
        val selectedVerse = args.verse

        val appViewModel = LocalAppViewModel.current

        LaunchedEffect(Unit) {
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = "Criar devocional",
                    showBackButton = true,
                    onBackNavigation = {
                        navController.popBackStack()
                    }
                )
            )
        }

        CreateDevocionalScreen(
            bookName = bookName.toString(),
            versicle = versicle,
            chapter = chapterNumber.toString(),
            verse = selectedVerse,
            state = devocionalStates,
            onSaveDevocional = {
                vmDevocional.saveDevocional(
                    bookName.toString(),
                    chapterNumber.toInt(),
                    versicle,
                    selectedVerse
                )
            },
            onSaveAsDraftDevocional = {
                vmDevocional.saveDevocional(
                    bookName.toString(),
                    chapterNumber.toInt(),
                    versicle,
                    selectedVerse,
                    isDraft = true
                )
            },
            onAddVerseToDevocional = {
                vmDevocional.addVerseToDevocional(it)
            }
        )
    }
}

fun NavController.navigateToChapterVerse(bookName: String?, chapterNumber: String) = this.navigateBetweenScreens(Screen.Verses(bookName = bookName, chapterNumber = chapterNumber) )
fun NavController.navigateToNoteVerse(bookName: String?, chapterNumber: String, verse: String, versicle: Int) = this.navigateBetweenScreens(Screen.NoteVerse(bookName = bookName, chapterNumber = chapterNumber, verse = verse, versicle = versicle))
fun NavController.navigateToCreateDevocional(bookName: String?, chapterNumber: String, versicle: Int, verse: String) = this.navigateBetweenScreens(Screen.CreateDevocional(bookName = bookName, chapterNumber = chapterNumber, versicle = versicle, verse = verse))