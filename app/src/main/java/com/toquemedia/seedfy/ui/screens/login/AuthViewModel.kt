package com.toquemedia.seedfy.ui.screens.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.seedfy.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onMyNote = {
                    _uiState.value = _uiState.value.copy(myNote = it)
                },
                user = repository.getCurrentUser()
            )
        }
        //_uiState.value = _uiState.value.copy(user = )
        getMyNotes()
    }

    fun signIn(activityContext: Activity?) {
        viewModelScope.launch {
            activityContext?.let {
                _uiState.value = _uiState.value.copy(isLoading = true)
                try {
                    val user = repository.googleSignIn(it)
                    _uiState.value = _uiState.value.copy(user = user, isLoading = false)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }
    }

    fun saveMyNote(myNote: String) {
        viewModelScope.launch {
            repository.saveMyNotes(myNote)
        }
    }

    private fun getMyNotes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(myNote = repository.getMyNotes())
        }
    }
}