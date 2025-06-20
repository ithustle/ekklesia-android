package com.toquemedia.seedfy.ui.screens.bible.verses

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.seedfy.model.NoteEntity
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.model.StoryType
import com.toquemedia.seedfy.repository.AuthRepositoryImpl
import com.toquemedia.seedfy.repository.NoteRepositoryImpl
import com.toquemedia.seedfy.repository.PostRepositoryImpl
import com.toquemedia.seedfy.repository.VerseRepositoryImpl
import com.toquemedia.seedfy.ui.screens.bible.states.VerseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerseViewModel @Inject constructor(
    private val verseRepository: VerseRepositoryImpl,
    private val noteRepository: NoteRepositoryImpl,
    private val postRepository: PostRepositoryImpl,
    private val userRepository: AuthRepositoryImpl
) : ViewModel() {

    private val _uiState: MutableStateFlow<VerseUiState> = MutableStateFlow(VerseUiState())
    val uiState: StateFlow<VerseUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onSelectVerse = { verse, versicle ->
                    _uiState.value = _uiState.value.copy(
                        selectedVerse = verse,
                        versicle = versicle
                    )
                },
                onOpenDialogToShareToCommunity = { it, share ->
                    _uiState.value = _uiState.value.copy(openDialogToShareToCommunity = Pair(it, share))
                },
                onMarkVerse = {
                    _uiState.value = _uiState.value.copy(
                        markedVerse = it,
                    )
                },
                onUnMarkVerse = { verse ->
                    val updatedVerses = _uiState.value.markedVerses.filter { it != verse }
                    _uiState.value = _uiState.value.copy(
                        markedVerses = updatedVerses
                    )
                },
                onShowAddNote = {
                    _uiState.value = _uiState.value.copy(showAddNote = it)
                },
                onShowVerseAction = {
                    _uiState.value = _uiState.value.copy(showVerseActionOption = it)
                },
                onEntryNoteChange = {
                    _uiState.value = _uiState.value.copy(entryNote = it)
                },
                onChangeChapter = {
                    _uiState.value = _uiState.value.copy(chapter = it)
                }
            )
        }

        viewModelScope.launch {
            verseRepository.getMarkedVerse().collect { preference ->
                val verses = mutableListOf<String>()
                for (key in preference.asMap().keys) {
                    val value = preference[key].toString()
                    verses.add(value)
                }
                _uiState.update { it.copy(markedVerses = verses) }
            }
        }
        viewModelScope.launch {
            try {
                noteRepository.getAllNotes().collect { notes ->
                    _uiState.update { it.copy(notes = notes) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun changeChapter(chapter: Int) {
        _uiState.value = _uiState.value.copy(chapter = chapter)
    }

    fun markVerse(bookName: String?, chapter: String?, versicle: String, verse: String, communityId: String) {
        viewModelScope.launch {
            if (bookName == null || chapter == null) return@launch
            verseRepository.markVerse(bookName, chapter.toInt(), versicle.toInt(), verse)
            _uiState.value.onMarkVerse(verse)
            _uiState.value.onSelectVerse("", -1)
            _uiState.value.onShowVerseAction(false)

            launch {
                val verseId = verseRepository.getId(bookName, chapter.toInt(), versicle.toInt())
                val post = PostType(
                    verse = verse,
                    user = userRepository.getCurrentUser(),
                    verseId = verseId,
                    communityId = communityId
                )
                postRepository.addPost(post, communityId)
            }
        }
    }

    fun unMarkVerse(bookName: String?, chapter: String?, versicle: String, verse: String) {
        viewModelScope.launch {
            if (bookName == null || chapter == null) return@launch
            verseRepository.unMarkVerse(bookName, chapter.toInt(), versicle.toInt())
            _uiState.value.onUnMarkVerse(verse)

            launch {
                val verseId = verseRepository.getId(bookName, chapter.toInt(), versicle.toInt())
                postRepository.removePost(verseId)
            }
        }
    }

    fun addNoteToVerse(
        bookName: String,
        chapter: Int,
        versicle: Int,
        verse: String,
        communityId: String
    ) {
        _uiState.value.onSavingNote(true)
        viewModelScope.launch {
            val note = NoteEntity(
                bookName = bookName,
                chapter = chapter,
                versicle = versicle,
                verse = verse,
                note = _uiState.value.entryNote,
                id = "${bookName}_${chapter}_$versicle",
            )
            noteRepository.addNoteToVerse(note)
            launch {
                val verseId = verseRepository.getId(bookName, chapter.toInt(), versicle.toInt())
                val post = PostType(
                    note = note,
                    verse = verse,
                    user = userRepository.getCurrentUser(),
                    verseId = "${verseId}_note",
                    communityId = communityId
                )
                postRepository.addPost(post, communityId)
            }
            _uiState.value.onShowAddNote(false)
            _uiState.value.onSelectVerse("", -1)
            _uiState.value.onEntryNoteChange("")
            _uiState.value.onSavingNote(false)
        }
    }

    fun addStoryToCommunity(selectedVerse: String, bookNameWithVersicle: String, backgroundColor: Int, verseColor: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(addingStory = true) }
            val story = StoryType(
                verse = selectedVerse,
                user = userRepository.getCurrentUser(),
                bookNameWithVersicle = bookNameWithVersicle,
                communityId = userRepository.getCommunitiesId(),
                backgroundColor = backgroundColor,
                verseColor = verseColor
            )
            verseRepository.addStoryToCommunity(story)
            _uiState.update { it.copy(addingStory = false) }
        }
    }
}