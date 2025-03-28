package com.toquemedia.ekklesia

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.toquemedia.ekklesia.model.BibleType
import com.toquemedia.ekklesia.model.BookType
import com.toquemedia.ekklesia.model.UserType
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
        internal set

    var topBarTitle by mutableStateOf("")

    var showBackgroundOverlay by mutableStateOf(false)

    var activityContext: Activity? = null
        get() = field
        set(value) {
            field = value
        }
}