package com.toquemedia.ekklesia.ui.navigation

import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.toquemedia.ekklesia.LocalAppViewModel
import com.toquemedia.ekklesia.model.TopBarState
import com.toquemedia.ekklesia.routes.Screen
import com.toquemedia.ekklesia.routes.navigateBetweenScreens
import com.toquemedia.ekklesia.ui.screens.bible.notes.NoteScreen
import com.toquemedia.ekklesia.ui.screens.bible.verses.VerseActionOption
import com.toquemedia.ekklesia.ui.screens.bible.verses.VerseViewModel
import com.toquemedia.ekklesia.ui.screens.bible.verses.VersesScreen
import com.toquemedia.ekklesia.ui.screens.bible.worship.CreateWorshipScreen
import com.toquemedia.ekklesia.ui.screens.bible.worship.VideoCreator
import com.toquemedia.ekklesia.ui.screens.bible.worship.WorshipViewModel
import com.toquemedia.ekklesia.ui.screens.community.feed.story.CreateStoryScreen
import com.toquemedia.ekklesia.ui.screens.community.feed.worshipPost.VideoPlayerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.verseNavigation(
    navController: NavController
) {

    composable<Screen.Verses> { backStackEntry ->

        val bibleGraphEntry = remember(navController.currentBackStackEntry) {
            navController.getBackStackEntry(Screen.BibleScreenGraph)
        }

        val vmVerses: VerseViewModel = hiltViewModel()
        val vmWorship: WorshipViewModel = hiltViewModel(bibleGraphEntry)

        val versesStates by vmVerses.uiState.collectAsState()
        val worshipStates by vmWorship.uiState.collectAsState()

        val args = backStackEntry.toRoute<Screen.Verses>()

        val bookName = args.bookName

        val scope = rememberCoroutineScope()
        val scrollState = rememberScrollState()

        val appViewModel = LocalAppViewModel.current

        val book = appViewModel.books.find { it.bookName == bookName }

        LaunchedEffect(versesStates.chapter) {
            appViewModel.showTopBar = true
            appViewModel.updateTopBarState(
                newState = TopBarState(
                    title = "Capítulo ${versesStates.chapter}",
                    showBackButton = true,
                    onBackNavigation = {
                        navController.popBackStack()
                    }
                )
            )
        }

        LaunchedEffect(Unit) {
            versesStates.onChangeChapter(args.chapterNumber)
        }

        if (versesStates.showVerseActionOption) {
            VerseActionOption(
                hasNote = versesStates.notes.find { it.verse == versesStates.selectedVerse } != null,
                hasDevocional = worshipStates.worships.find { it.verse == versesStates.selectedVerse } != null,
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
                        versesStates.chapter.toString(),
                        versesStates.versicle.toString(),
                        versesStates.selectedVerse
                    )
                },
                onAddNoteToVerse = {
                    navController.navigateToNoteVerse(
                        bookName,
                        versesStates.chapter.toString(),
                        versesStates.selectedVerse,
                        versesStates.versicle
                    )
                    versesStates.apply {
                        onShowVerseAction(false)
                        onSelectVerse("", -1)
                    }
                },
                onSelectVerseForDevocional = {
                    navController.navigateToCreateWorship(
                        bookName,
                        versesStates.chapter.toString(),
                        versesStates.versicle,
                        versesStates.selectedVerse
                    )
                    versesStates.apply {
                        onShowVerseAction(false)
                        onSelectVerse("", -1)
                    }
                },
                onAddStory = {
                    navController.navigateToStoryCreator(
                        verse = versesStates.selectedVerse,
                        bookWithVersicle = "$bookName ${versesStates.chapter}:${versesStates.versicle}"
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
            chapterNumber = versesStates.chapter,
            scrollState = scrollState,
            versesStates = versesStates,
            worshipState = worshipStates,
            onSelectedVerse = { verse, versicle ->
                versesStates.onSelectVerse(verse, versicle)
            },
            onUnMarkVerse = { verse, versicle ->
                vmVerses.unMarkVerse(
                    bookName = bookName,
                    chapter = versesStates.chapter.toString(),
                    versicle = versicle.toString(),
                    verse = verse
                )
            },
            onNextVerse = { versicle ->
                if (versicle <= versesStates.chapter) {
                    vmVerses.changeChapter(versicle + 1)
                    scope.launch {
                        scrollState.scrollTo(0)
                    }
                }
            },
            onPreviousVerse = { versicle ->
                if (versicle > 1) {
                    vmVerses.changeChapter(versicle - 1)
                    scope.launch {
                        scrollState.scrollTo(0)
                    }
                }
            },
        )
    }

    composable<Screen.StoryCreator> {
        val bibleGraphEntry = remember(navController.currentBackStackEntry) {
            navController.getBackStackEntry(Screen.BibleScreenGraph)
        }

        val args = it.toRoute<Screen.StoryCreator>()

        val vmVerses: VerseViewModel = hiltViewModel(bibleGraphEntry)
        val versesStates by vmVerses.uiState.collectAsState()

        val appViewModel = LocalAppViewModel.current

        LaunchedEffect(Unit) {
            appViewModel.showTopBar = false
        }

        CreateStoryScreen(
            verse = args.verse,
            addingStory = versesStates.addingStory,
            bookNameWithVersicle = args.bookWithVersicle,
            onPublishStory = {
                vmVerses.addStoryToCommunity(
                    selectedVerse = args.verse,
                    bookNameWithVersicle = args.bookWithVersicle,
                    backgroundColor = it.first.toArgb(),
                    verseColor = it.second.toArgb()
                )
            },
            onFinishPublishedStory = {
                navController.popBackStack()
            }
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

    composable<Screen.CreateWorship> { backStackEntry ->

        val bibleGraphEntry = remember(navController.currentBackStackEntry) {
            navController.getBackStackEntry(Screen.BibleScreenGraph)
        }

        val vmWorship: WorshipViewModel = hiltViewModel(bibleGraphEntry)
        val worshipStates by vmWorship.uiState.collectAsState()

        val args = backStackEntry.toRoute<Screen.CreateWorship>()

        val chapterNumber = args.chapterNumber
        val bookName = args.bookName
        val versicle = args.versicle
        val selectedVerse = args.verse

        val appViewModel = LocalAppViewModel.current
        val context = LocalContext.current

        val videoPathResult by backStackEntry.savedStateHandle
            .getStateFlow<String?>("videoPath", null)
            .collectAsState()

        LaunchedEffect(Unit) {
            appViewModel.showTopBar = true
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

        CreateWorshipScreen(
            context = context,
            bookName = bookName.toString(),
            versicle = versicle,
            chapter = chapterNumber.toString(),
            verse = selectedVerse,
            state = worshipStates,
            videoPathResult = videoPathResult,
            onSaveWorship = {
                vmWorship.saveWorship(
                    bookName.toString(),
                    chapterNumber.toInt(),
                    versicle,
                    selectedVerse,
                    video = worshipStates.videoUri
                )
                navController.popBackStack()
            },
            onCreateVideoForWorship = {
                navController.navigateToCreateVideoForWorship()
            },
            onAddVerseToWorship = {
                vmWorship.addVerseToWorship(it)
            },
            onDeleteWorshipVideo = {
                videoPathResult?.let {
                    vmWorship.deleteRecordedVideo(it)
                }
            }
        )
    }

    composable<Screen.CreateVideo> {

        val bibleGraphEntry = remember(navController.currentBackStackEntry) {
            navController.getBackStackEntry(Screen.BibleScreenGraph)
        }

        val viewModel: WorshipViewModel = hiltViewModel(bibleGraphEntry)

        val vmPlayer: VideoPlayerViewModel = hiltViewModel(bibleGraphEntry)
        val playerState by vmPlayer.uiState.collectAsStateWithLifecycle()

        val appViewModel = LocalAppViewModel.current
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        LaunchedEffect(Unit) {
            appViewModel.showTopBar = false
        }

        VideoCreator(
            context = context,
            lifecycleOwner = lifecycleOwner,
            player = playerState.player,
            onSaveRecording = {
                navController.previousBackStackEntry?.savedStateHandle?.set("videoPath", it)
                navController.popBackStack()
                viewModel.uploadRecordedVideo(it)
            },
            onCancelRecording = {
                navController.popBackStack()
            },
            onDeleteRecord = {
                navController.previousBackStackEntry?.savedStateHandle?.set("videoPath", it)
                viewModel.deleteRecordedVideo(it)
            }
        )
    }
}

fun NavController.navigateToChapterVerse(bookName: String?, chapterNumber: Int) = this.navigateBetweenScreens(Screen.Verses(bookName = bookName, chapterNumber = chapterNumber) )
fun NavController.navigateToNoteVerse(bookName: String?, chapterNumber: String, verse: String, versicle: Int) = this.navigateBetweenScreens(Screen.NoteVerse(bookName = bookName, chapterNumber = chapterNumber, verse = verse, versicle = versicle))
fun NavController.navigateToCreateWorship(bookName: String?, chapterNumber: String, versicle: Int, verse: String) = this.navigateBetweenScreens(Screen.CreateWorship(bookName = bookName, chapterNumber = chapterNumber, versicle = versicle, verse = verse))
fun NavController.navigateToCreateVideoForWorship() = this.navigateBetweenScreens(Screen.CreateVideo)
fun NavController.navigateToStoryCreator(verse: String, bookWithVersicle: String) = this.navigateBetweenScreens(Screen.StoryCreator(verse = verse, bookWithVersicle = bookWithVersicle))