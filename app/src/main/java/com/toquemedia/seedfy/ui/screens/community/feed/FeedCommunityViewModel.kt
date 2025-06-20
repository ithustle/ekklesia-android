package com.toquemedia.seedfy.ui.screens.community.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.toquemedia.seedfy.dao.AppCacheDao
import com.toquemedia.seedfy.model.CommentType
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.repository.AuthRepositoryImpl
import com.toquemedia.seedfy.repository.PostRepositoryImpl
import com.toquemedia.seedfy.repository.VerseRepositoryImpl
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
    private val user: AuthRepositoryImpl,
    private val verseRepository: VerseRepositoryImpl,
    private val cache: AppCacheDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedCommunityUiState>(FeedCommunityUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onChangeTextComment = {
                    _uiState.value = _uiState.value.copy(textComment = it)
                },
                onUserLikes = { postId, like ->
                    val updatedMap = _uiState.value.userLikes.toMutableMap()

                    if (like > 0) {
                        updatedMap[postId] = 1
                    } else {
                        updatedMap.remove(postId)
                    }
                    _uiState.value = _uiState.value.copy(userLikes = updatedMap)
                }
            )
        }
        println("VIEW MODEL FeedCommunityViewModel")
        getUserLiked()
    }

    fun addCommentOnPost(communityId: String) {
        viewModelScope.launch {

            val selectedPost = _uiState.value.selectedPost ?: return@launch

            val comment = CommentType(
                user = user.getCurrentUser(),
                comment = _uiState.value.textComment,
                communityId = communityId,
                postId = selectedPost.verseId
            )

            val updatedPost = selectedPost.copy(
                comments = selectedPost.comments + comment
            )

            _uiState.update { state ->
                state.copy(
                    selectedPost = updatedPost,
                    sendingComment = true,
                    textComment = "",
                    posts = state.posts.map {
                        if (it.verseId == updatedPost.verseId) updatedPost else it
                    }
                )
            }

            try {
                repository.addComment(postId = selectedPost.verseId, communityId = communityId, comment = comment)
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { state ->
                    state.copy(
                        selectedPost = selectedPost,
                        sendingComment = false,
                        posts = state.posts.map {
                            if (it.verseId == selectedPost.verseId) selectedPost else it
                        }
                    )
                }
            } finally {
                _uiState.update { state ->
                    state.copy(sendingComment = false)
                }
            }
        }
    }

    fun selectPost(postId: String) {
        val post = _uiState.value.posts.firstOrNull { it.verseId == postId }
        _uiState.update {
            it.copy(selectedPost = post)
        }
    }

    fun selectPost(post: PostType) {
        _uiState.update {
            it.copy(selectedPost = post)
        }
    }

    fun likeAPost(post: PostType, communityId: String, isRemoving: Boolean = false) {
        viewModelScope.launch {
            user.getCurrentUser()?.let {
                if (isRemoving) {
                    repository.removeLikeOnPost(post.verseId, communityId)
                } else {
                    repository.addLikeOnPost(post.verseId, communityId, it)
                }
            }
        }
    }

    fun getAllPosts(communityId: String) {
        viewModelScope.launch {

            val cachedPosts = cache.getCache<List<PostType>>(communityId, object : TypeToken<List<PostType>>() {})

            if (cachedPosts == null) {
                _uiState.update { it.copy(loadingPosts = true) }
            } else {
                _uiState.update { it.copy(posts = cachedPosts) }
            }

            val posts = repository.getPosts(communityId)

            val all = posts.map {
                async {
                    val comments = repository.getComments(postId = it.verseId, communityId = communityId, limit = 15)
                    it.copy(comments = comments)
                }
            }.awaitAll()

            cache.saveCache(communityId, all)
            _uiState.update { it.copy(posts = all, loadingPosts = false) }
        }
    }

    fun getStories(communityId: String) {
        viewModelScope.launch {
            val stories = verseRepository.getStories(communityId)
            _uiState.update { it.copy(stories = stories) }
        }
    }

    private fun getUserLiked() {
        viewModelScope.launch {
            repository.getUserLikesOnPost().collect { likes ->

                val currentLikes = _uiState.value.userLikes.toMutableMap()

                likes.distinct().forEach { postId ->
                    currentLikes[postId] = 1
                }

                _uiState.update { state ->
                    state.copy(
                        likedPosts = likes,
                        userLikes = currentLikes
                    )
                }
            }
        }
    }
}