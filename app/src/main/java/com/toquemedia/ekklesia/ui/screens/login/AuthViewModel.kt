package com.toquemedia.ekklesia.ui.screens.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.toquemedia.ekklesia.repository.AuthRepositoryImpl
import com.toquemedia.ekklesia.utils.PhoneAuthResult
import com.toquemedia.ekklesia.utils.PhoneAuthVerificationResult
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

    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var currentPhoneNumber: String = ""

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

    fun sendPhoneVerificationCode(phoneNumber: String, activity: Activity) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            currentPhoneNumber = phoneNumber

            repository.sendPhoneVerificationCode(phoneNumber, activity).collect { result ->
                when (result) {
                    is PhoneAuthResult.CodeSent -> {
                        resendToken = result.token
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            codeSent = true,
                            verificationId = result.verificationId,
                            resendCode = { resendCode(activity) }
                        )
                    }
                    is PhoneAuthResult.VerificationCompleted -> {
                        signInWithPhoneCredential(result.credential)
                    }
                    is PhoneAuthResult.VerificationFailed -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.error
                        )
                    }
                }
            }
        }
    }

    fun verifyPhoneCode(code: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val verificationId = _uiState.value.verificationId
            if (verificationId != null) {
                try {
                    val result = repository.verifyPhoneCode(verificationId, code)
                    when (result) {
                        is PhoneAuthVerificationResult.Success -> {
                            _uiState.value = _uiState.value.copy(
                                user = result.user,
                                isLoading = false
                            )
                        }
                        is PhoneAuthVerificationResult.NewUser -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                needsProfileCompletion = true,
                                tempUserId = result.userId
                            )
                        }
                        is PhoneAuthVerificationResult.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Erro ao verificar código: ${e.message}"
                    )
                }
            }
        }
    }

    fun completeProfile(firstName: String, lastName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val userId = _uiState.value.tempUserId
            if (userId != null) {
                try {
                    val user = repository.updateUserProfile(userId, "$firstName $lastName")
                    _uiState.value = _uiState.value.copy(
                        user = user,
                        isLoading = false,
                        needsProfileCompletion = false
                    )
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Erro ao salvar perfil: ${e.message}"
                    )
                }
            }
        }
    }

    fun saveMyNote(myNote: String) {
        viewModelScope.launch {
            repository.saveMyNotes(myNote)
        }
    }

    fun resetPhoneAuth() {
        _uiState.value = _uiState.value.copy(
            codeSent = false,
            verificationId = null,
            error = null
        )
    }

    private fun resendCode(activity: Activity) {
        viewModelScope.launch {
            resendToken?.let { token ->
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                repository.resendPhoneCode(currentPhoneNumber, activity, token).collect { result ->
                    when (result) {
                        is PhoneAuthResult.CodeSent -> {
                            resendToken = result.token
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                verificationId = result.verificationId,
                                error = "Código reenviado"
                            )
                        }
                        is PhoneAuthResult.VerificationCompleted -> {
                            signInWithPhoneCredential(result.credential)
                        }
                        is PhoneAuthResult.VerificationFailed -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = result.error
                            )
                        }
                    }
                }
            }
        }
    }

    // No AuthViewModel
    private fun signInWithPhoneCredential(credential: PhoneAuthCredential) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val result = repository.signInWithCredential(credential)
                when (result) {
                    is PhoneAuthVerificationResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            user = result.user,
                            isLoading = false
                        )
                    }
                    is PhoneAuthVerificationResult.NewUser -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            needsProfileCompletion = true,
                            tempUserId = result.userId
                        )
                    }
                    is PhoneAuthVerificationResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Erro no login automático: ${e.message}"
                )
            }
        }
    }

    private fun getMyNotes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(myNote = repository.getMyNotes())
        }
    }
}