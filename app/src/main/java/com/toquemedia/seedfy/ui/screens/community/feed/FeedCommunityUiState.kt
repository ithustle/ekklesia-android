package com.toquemedia.seedfy.ui.screens.community.feed

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.toquemedia.seedfy.model.PostType
import com.toquemedia.seedfy.model.StoryType

data class FeedCommunityUiState(
    val posts: List<PostType> = emptyList(),
    val selectedPost: PostType? = null,
    val textComment: String = "",
    val likedPosts: List<String> = emptyList(),
    val userLikes: Map<String, Long> =emptyMap(),
    val onUserLikes: (String, Long) -> Unit = {_, _ ->},
    val stories: List<StoryType> = emptyList(),
    val loadingPosts: Boolean = false,
    val sendingComment: Boolean = false,
    val onChangeTextComment: (String) -> Unit = {}
)