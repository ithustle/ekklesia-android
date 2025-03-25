package com.toquemedia.ekklesia.ui.screens.community.feed

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class FeedCommunityViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedCommunityUiState>(FeedCommunityUiState())
    val uiState = _uiState.asStateFlow()



}