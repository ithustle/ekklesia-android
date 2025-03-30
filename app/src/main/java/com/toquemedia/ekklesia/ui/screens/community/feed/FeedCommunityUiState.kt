package com.toquemedia.ekklesia.ui.screens.community.feed

import com.toquemedia.ekklesia.model.PostType

data class FeedCommunityUiState(
    val posts: List<PostType> = emptyList(),
    val loadingPosts: Boolean = false
)