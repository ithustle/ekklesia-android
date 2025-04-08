package com.toquemedia.ekklesia.ui.screens.community.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.model.MessageEntity
import com.toquemedia.ekklesia.model.MessageType
import com.toquemedia.ekklesia.repository.CommunityRepositoryImpl
import com.toquemedia.ekklesia.repository.MessageRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: MessageRepositoryImpl,
    communityRepository: CommunityRepositoryImpl
): ViewModel() {

    private val _uiState: MutableStateFlow<ChatUiState> = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onCommunityIdChange = { communityId ->
                    _uiState.value = _uiState.value.copy(communityId = communityId)
                    getAllMessages(communityId)
                }
            )
        }
    }

    fun sendMessage(message: MessageEntity) {
        viewModelScope.launch {
            repository.sendMessage(message = message)
        }
    }

    private fun getAllMessages(communityId: String) {
        viewModelScope.launch {
            repository.getMessages(communityId).collect {
                _uiState.value = _uiState.value.copy(messages = it)
            }
        }
    }
}