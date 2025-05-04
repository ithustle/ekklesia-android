package com.toquemedia.ekklesia.ui.screens.community.feed

import com.toquemedia.ekklesia.model.PostType
import com.toquemedia.ekklesia.model.StoryType

data class FeedCommunityUiState(
    val posts: List<PostType> = emptyList(),
    val selectedPost: PostType? = null,
    val textComment: String = "",
    val likedPosts: List<String> = emptyList(),
    val stories: List<StoryType> = emptyList(),
    val loadingPosts: Boolean = false,
    val sendingComment: Boolean = false,
    val onChangeTextComment: (String) -> Unit = {}
)