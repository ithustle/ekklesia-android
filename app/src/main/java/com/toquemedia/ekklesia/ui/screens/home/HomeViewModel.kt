package com.toquemedia.ekklesia.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.model.VerseType
import com.toquemedia.ekklesia.repository.BibleRepositoryImpl
import com.toquemedia.ekklesia.repository.CommunityRepositoryImpl
import com.toquemedia.ekklesia.repository.VerseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val verseRepository: VerseRepositoryImpl,
    private val communityRepository: CommunityRepositoryImpl,
    private val bibleRepository: BibleRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getVerseOfDay()
    }

    private fun getVerseOfDay() {
        viewModelScope.launch {
            val result = verseRepository.getVerseOfDay()
            val books = bibleRepository.getBooks()

            result?.let { res ->
                val verses = books.find { it.bookName == res.first }?.verses

                _uiState.value = _uiState.value.copy(
                    verseOfDay = VerseType(
                        bookName = result.first,
                        chapter = result.second,
                        versicle = result.third,
                        text = verses?.get(res.second)?.get(res.third).toString()
                    )
                )
            }
        }
    }

    private fun getAllCommunity() {

    }
}