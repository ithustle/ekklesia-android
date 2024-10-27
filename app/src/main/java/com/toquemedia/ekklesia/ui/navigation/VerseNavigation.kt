package com.toquemedia.ekklesia.ui.navigation

import Screen
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.toquemedia.ekklesia.ui.screens.bible.TestamentViewModel
import com.toquemedia.ekklesia.ui.screens.bible.devocional.CreateDevocionalScreen
import com.toquemedia.ekklesia.ui.screens.bible.devocional.DevocionalViewModel
import com.toquemedia.ekklesia.ui.screens.bible.notes.ModalNoteScreen
import com.toquemedia.ekklesia.ui.screens.bible.verses.VerseActionOption
import com.toquemedia.ekklesia.ui.screens.bible.verses.VerseViewModel
import com.toquemedia.ekklesia.ui.screens.bible.verses.VersesScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.verseNavigation(
    showDevocionalModal: (@Composable () -> Unit) -> Unit = {},
    hideDevocionalModal: () -> Unit = {}
) {
    composable("${Screen.Verses.route}/{bookName}/{chapterNumber}") { backStackEntry ->
        val vmTestament: TestamentViewModel = hiltViewModel()
        val vmVerses: VerseViewModel = hiltViewModel()
        val vmDevocional: DevocionalViewModel = hiltViewModel()

        val testamentStates by vmTestament.uiState.collectAsState()
        val versesStates by vmVerses.uiState.collectAsState()
        val devocionalStates by vmDevocional.uiState.collectAsState()

        val chapterNumber = backStackEntry.arguments?.getString("chapterNumber")
        val bookName = backStackEntry.arguments?.getString("bookName")
        val scope = rememberCoroutineScope()
        val scrollState = rememberScrollState()
        val context = LocalContext.current

        bookName?.let {
            vmTestament.getVersesOfTheChapter(it)
        }

        if (versesStates.showVerseActionOption) {
            VerseActionOption(
                onDismissRequest = { sheetState ->
                    versesStates.onSelectVerse("", -1)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            versesStates.onShowVerseAction(false)
                        }
                    }
                },
                onFavoriteVerse = {
                    vmVerses.markVerse(bookName, chapterNumber, versesStates.versicle.toString(), versesStates.selectedVerse)
                },
                onAddNoteToVerse = {
                    versesStates.apply {
                        onShowAddNote(true)
                        onShowVerseAction(false)
                    }
                },
                onSelectVerseForDevocional = {
                    showDevocionalModal {
                        CreateDevocionalScreen(
                            bookName = bookName.toString(),
                            versicle = versesStates.versicle,
                            chapter = chapterNumber.toString(),
                            verse = versesStates.selectedVerse,
                            state = devocionalStates,
                            onSaveDevocional = {
                                vmDevocional.saveDevocional(
                                    bookName.toString(),
                                    chapterNumber?.toInt(),
                                    versesStates.versicle,
                                    versesStates.selectedVerse
                                )
                                versesStates.onSelectVerse("", -1)
                                hideDevocionalModal()
                            },
                            onSaveAsDraftDevocional = {
                                vmDevocional.saveDevocional(
                                    bookName.toString(),
                                    chapterNumber?.toInt(),
                                    versesStates.versicle,
                                    versesStates.selectedVerse,
                                    isDraft = true
                                )
                                versesStates.onSelectVerse("", -1)
                                hideDevocionalModal()
                            },
                            onAddVerseToDevocional = {
                                vmDevocional.addVerseToDevocional(it)
                            }
                        )
                    }
                    versesStates.apply {
                        onShowVerseAction(false)
                    }
                }
            )
        }

        if (versesStates.showAddNote) {
            ModalNoteScreen(
                onDismissRequest = { sheetState ->
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            versesStates.onShowAddNote(false)
                            versesStates.onSelectVerse("", -1)
                        }
                    }
                },
                verse = versesStates.selectedVerse,
                bookName = testamentStates.book?.bookName.toString(),
                versicle = versesStates.versicle,
                chapter = chapterNumber.toString(),
                entryNote = versesStates.entryNote,
                onEntryNoteChange = versesStates.onEntryNoteChange,
                savingNote = versesStates.savingNote,
                onSaveNote = {
                    vmVerses.addNoteToVerse(
                        bookName = bookName.toString(),
                        chapter = chapterNumber?.toInt() ?: 0,
                        versicle = versesStates.versicle,
                        verse = versesStates.selectedVerse
                    )
                    Toast.makeText(context, "Nota adicionada com sucesso", Toast.LENGTH_SHORT).show()
                },
                onSaveAndShareNote = {
                    vmVerses.saveAndShareNote(
                        bookName = bookName.toString(),
                        chapter = chapterNumber?.toInt() ?: 0,
                        versicle = versesStates.versicle,
                        verse = versesStates.selectedVerse
                    )
                    Toast.makeText(context, "Nota salva e partilhada com sucesso", Toast.LENGTH_SHORT).show()
                }
            )
        }

        VersesScreen(
            book = testamentStates.book,
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
        )
    }
}

fun NavHostController.navigateToChapterVerse(bookName: String?, chapterNumber: String) {
    this.navigate("${Screen.Verses.route}/${bookName}/${chapterNumber}") {
        launchSingleTop = true
        popUpTo("${Screen.Verses.route}/{bookName}/{chapterNumber}")
    }
}