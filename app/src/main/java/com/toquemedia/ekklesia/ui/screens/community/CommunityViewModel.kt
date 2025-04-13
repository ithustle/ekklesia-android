package com.toquemedia.ekklesia.ui.screens.community

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.extension.communitiesToJoin
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.ValidationResult
import com.toquemedia.ekklesia.repository.AuthRepositoryImpl
import com.toquemedia.ekklesia.repository.CommunityRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepositoryImpl,
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommunityUiState(
        loadCommunities = false,
        joiningToCommunity = false
    ))
    val uiState get() = _uiState.asStateFlow()

    private val _validationEvent = MutableSharedFlow<ValidationResult>()
    val validationEvent = _validationEvent.asSharedFlow()

    private val user = authRepository.getCurrentUser()

    init {
        _uiState.update { current ->
            current.copy(
                onImageUriChange = {
                    _uiState.value = _uiState.value.copy(imageUri = it)
                },
                onCommunityNameChange = {
                    _uiState.value = _uiState.value.copy(communityName = it)
                },
                onCommunityDescriptionChange = {
                    _uiState.value = _uiState.value.copy(communityDescription = it)
                },
                onOpenDialogChange = {
                    _uiState.value = _uiState.value.copy(openDialog = it)
                },
                userPhoto = user?.photo?.toUri()
            )
        }

        println("CARREGA... DE NOVO!!!!")

        viewModelScope.launch {
            try {
                val (verseOfDay, communities) = coroutineScope {
                    val communitiesDeferred = async { getAllCommunities() }
                    val communitiesInDeferred = async { getAllCommunitiesUserIn() }
                    communitiesInDeferred.await() to communitiesDeferred.await()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createCommunity() {
        viewModelScope.launch {
            _uiState.value.let {
                _validationEvent.emit(ValidationResult.Loading)
                when {
                    it.communityName.isEmpty() -> {
                        _validationEvent.emit(ValidationResult.Error(message = "Nome da comunidade não pode estar vazio"))
                        return@launch
                    }

                    it.communityDescription.isEmpty() -> {
                        _validationEvent.emit(ValidationResult.Error(message = "Adicione uma descrição para a sua comunidade"))
                        return@launch
                    }

                    it.imageUri.isEmpty() -> {
                        _validationEvent.emit(ValidationResult.Error(message = "É obrigatório uma imagem para a comunidade"))
                        return@launch
                    }
                    else -> {
                        try {
                            repository.createCommunity(
                                name = it.communityName,
                                description = it.communityDescription,
                                image = it.imageUri,
                                user = user
                            )
                            _validationEvent.emit(ValidationResult.Success)
                        } catch (e: Exception) {
                            _validationEvent.emit(ValidationResult.Error(message = "Erro ao criar comunidade: ${e.message}"))
                        }
                    }
                }
            }
        }
    }

    fun joinToCommunity(communityId: String) {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()

            _uiState.value = _uiState.value.copy(joiningToCommunity = true)

            user?.let { user ->
                val member = CommunityMemberType(
                    id = user.id,
                    user = user,
                    isAdmin = false
                )
                repository.addMember(
                    communityId = communityId,
                    member = member
                )
                _uiState.value = _uiState.value.copy(
                    joiningToCommunity = false,
                    communities = _uiState.value.communities.filter { it.community?.id != communityId }
                )
            }
        }
    }

    fun getCurrentCommunity(communityId: String): CommunityWithMembers? {
        return _uiState.value.communitiesUserIn.find {
            it.community?.id == communityId
        }
    }

    fun deleteCommunity(communityId: String) {
        viewModelScope.launch {
            repository.deleteCommunity(communityId)
        }
    }

    private fun getAllCommunities() {
        viewModelScope.launch {
            try {
                val user = authRepository.getCurrentUser()
                user?.email?.let {
                    val communities = repository.getAll(it)

                    withContext(Dispatchers.IO) {
                        val all = communities.map { community ->
                            async {
                                val members = repository.getAllMembers(community.id)
                                CommunityWithMembers(community, members)
                            }
                        }.awaitAll()

                        _uiState.value = _uiState.value.copy(
                            communities = all.communitiesToJoin(user.id),
                            loadCommunities = false
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    private fun getAllCommunitiesUserIn() {
        viewModelScope.launch {
            repository.getCommunitiesUserInIds().collect {
                val communities = repository.getCommunitiesUserIn(it)
                _uiState.value = _uiState.value.copy(communitiesUserIn = communities, loadingCommunitiesUserIn = false)
            }
        }
    }

    /*private fun getAllLocalCommunities() {
        viewModelScope.launch {
            repository.getAll().collect {
                val all = it.map { community ->
                    async {
                        val members = repository.getAllMembers(community.id)
                        CommunityWithMembers(community, members)
                    }
                }.awaitAll()

                _uiState.value = _uiState.value.copy(communities = all)
            }
        }
    }*/
}