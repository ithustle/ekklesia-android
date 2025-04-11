package com.toquemedia.ekklesia

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.toquemedia.ekklesia.extension.getGreeting
import com.toquemedia.ekklesia.model.BibleType
import com.toquemedia.ekklesia.model.BookType
import com.toquemedia.ekklesia.model.TopBarState
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.model.interfaces.AuthRepository
import com.toquemedia.ekklesia.model.interfaces.BibleRepository
import com.toquemedia.ekklesia.routes.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    bibleRepository: BibleRepository,
    authRepository: AuthRepository,
) : ViewModel() {

    var currentUser by mutableStateOf<UserType?>(authRepository.getCurrentUser())
        internal set

    private val _topBarState = mutableStateOf(TopBarState(title = "${Date().getGreeting()}, ${currentUser?.displayName}"))
    val topBarState: State<TopBarState> = _topBarState

    var bible by mutableStateOf<List<BibleType>>(bibleRepository.loadBible())
        private set

    var books by mutableStateOf<List<BookType>>(bibleRepository.getBooks())
        private set

    var showBackgroundOverlay by mutableStateOf(false)

    var activityContext: Activity? = null
        get() = field
        set(value) {
            field = value
        }

    fun updateTopBarState(newState: TopBarState) {
        _topBarState.value = newState
    }
}