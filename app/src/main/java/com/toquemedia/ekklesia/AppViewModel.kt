package com.toquemedia.ekklesia

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.toquemedia.ekklesia.model.BibleType
import com.toquemedia.ekklesia.model.BookType
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.model.VerseType
import com.toquemedia.ekklesia.model.interfaces.AuthRepository
import com.toquemedia.ekklesia.model.interfaces.BibleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    bibleRepository: BibleRepository,
    authRepository: AuthRepository,
) : ViewModel() {

    var bible by mutableStateOf<List<BibleType>>(bibleRepository.loadBible())
        private set

    var books by mutableStateOf<List<BookType>>(bibleRepository.getBooks())
        private set

    var currentUser by mutableStateOf<UserType?>(authRepository.getCurrentUser())
        private set

    var verseOfTheDay by mutableStateOf<VerseType?>(null)
        private set

    fun getVerseOfDay(result: Triple<String, Int, Int>?) {
        result?.let { res ->
            val verses = books.find { it.bookName == res.first }?.verses

            verseOfTheDay = VerseType(
                bookName = result.first,
                chapter = result.second,
                versicle = result.third,
                text = verses?.get(res.second)?.get(res.third).toString()
            )
        }
    }
}