package com.toquemedia.ekklesia

import android.app.Activity
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.extension.getGreeting
import com.toquemedia.ekklesia.model.BibleType
import com.toquemedia.ekklesia.model.BookType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.TopBarState
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.model.WorshipEntity
import com.toquemedia.ekklesia.repository.AuthRepositoryImpl
import com.toquemedia.ekklesia.repository.BibleRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    bibleRepository: BibleRepositoryImpl,
    private val authRepository: AuthRepositoryImpl,
) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserType?>(authRepository.getCurrentUser())
    val currentUser: StateFlow<UserType?> = _currentUser.asStateFlow()

    private val _topBarState =
        mutableStateOf(TopBarState(title = "${Date().getGreeting()}, ${_currentUser.value?.displayName}"))
    val topBarState: State<TopBarState> = _topBarState

    var bible by mutableStateOf<List<BibleType>>(bibleRepository.loadBible())
        internal set

    var books by mutableStateOf<List<BookType>>(bibleRepository.getBooks())
        internal set

    var selectedCommunity by mutableStateOf<CommunityWithMembers?>(null)

    var videoPlayerVisible by mutableStateOf<Boolean>(false)

    var showBackgroundOverlay by mutableStateOf(false)

    var showTopBar by mutableStateOf(true)

    var activityContext: Activity? = null
        get() = field
        set(value) {
            field = value
        }

    fun updateTopBarState(newState: TopBarState) {
        _topBarState.value = newState
    }

    fun setCurrentUser(user: UserType?) {
        _currentUser.value = user
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.signOut()
            _currentUser.value = null
        }
    }
}