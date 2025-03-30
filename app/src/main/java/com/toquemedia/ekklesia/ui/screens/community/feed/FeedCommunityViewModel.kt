package com.toquemedia.ekklesia.ui.screens.community.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.repository.PostRepositoryImpl
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FeedCommunityViewModel @Inject constructor(
    private val repository: PostRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedCommunityUiState>(FeedCommunityUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllPosts()
    }

    private fun getAllPosts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loadingPosts = true)
            val posts = repository.getPosts()
            _uiState.value = _uiState.value.copy(posts = posts, loadingPosts = false)
        }
    }
}