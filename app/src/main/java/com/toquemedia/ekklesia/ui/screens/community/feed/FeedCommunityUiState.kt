package com.toquemedia.ekklesia.ui.screens.community.feed

import com.toquemedia.ekklesia.model.PostType

data class FeedCommunityUiState(
    val posts: List<PostType> = emptyList(),
    val selectedPost: PostType? = null,
    val textComment: String = "",
    val likedPosts: List<String> = emptyList(),
    val loadingPosts: Boolean = false,
    val sendingComment: Boolean = false,
    val onChangeTextComment: (String) -> Unit = {}
)