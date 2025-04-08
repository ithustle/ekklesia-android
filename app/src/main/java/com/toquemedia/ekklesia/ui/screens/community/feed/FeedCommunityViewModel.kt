package com.toquemedia.ekklesia.ui.screens.community.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.model.CommentType
import com.toquemedia.ekklesia.model.PostType
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.repository.AuthRepositoryImpl
import com.toquemedia.ekklesia.repository.PostRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedCommunityViewModel @Inject constructor(
    private val repository: PostRepositoryImpl,
    private val user: AuthRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedCommunityUiState>(FeedCommunityUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onChangeTextComment = {
                    _uiState.value = _uiState.value.copy(textComment = it)
                }
            )
        }

        getUserLiked()
    }

    fun addCommentOnPost() {
        viewModelScope.launch {

            val comment = CommentType(
                user = user.getCurrentUser(),
                comment = _uiState.value.textComment
            )

            val selectedPost = _uiState.value.selectedPost ?: return@launch

            val updatePost = selectedPost.copy(
                comments = selectedPost.comments + comment
            )

            _uiState.value = _uiState.value.copy(selectedPost = updatePost, sendingComment = true)

            try {
                repository.addComment(postId = selectedPost.verseId, comment = comment)
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    selectedPost = selectedPost.copy(comments = selectedPost.comments),
                    sendingComment = false
                )
            } finally {
                _uiState.value = _uiState.value.copy(sendingComment = false, textComment = "")
            }
        }
    }

    fun selectPost(postId: String) {
        _uiState.value =
            _uiState.value.copy(selectedPost = _uiState.value.posts.first { it.verseId == postId })
    }

    fun likeAPost(post: PostType, isRemoving: Boolean = false) {
        viewModelScope.launch {
            user.getCurrentUser()?.let {
                if (isRemoving) {
                    repository.removeLikeOnPost(post.verseId, it.id)
                } else {
                    repository.addLikeOnPost(post.verseId, it)
                }
            }
        }
    }

    fun getAllPosts(communityId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loadingPosts = true)

            val posts = repository.getPosts(communityId)

            val all = posts.map {
                async {
                    val comments = repository.getComments(it.verseId, limit = 15)
                    val likes = repository.getLikesOnPost(it.verseId)
                    it.copy(comments = comments, firstUsersLiked = likes)
                }
            }.awaitAll()

            _uiState.value = _uiState.value.copy(posts = all, loadingPosts = false)
        }
    }

    private fun getUserLiked() {
        viewModelScope.launch {
            repository.getUserLikesOnPost().collect {
                _uiState.value = _uiState.value.copy(likedPosts = it)
            }
        }
    }
}