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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepositoryImpl,
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommunityUiState())
    val uiState = _uiState.asStateFlow()

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

                    it.imageUri.toString().isEmpty() -> {
                        _validationEvent.emit(ValidationResult.Error(message = "É obrigatório uma imagem para a comunidade"))
                        return@launch
                    }

                    else -> {
                        try {
                            val newCommunity = repository.createCommunity(
                                name = it.communityName,
                                description = it.communityDescription,
                                image = it.imageUri,
                                user = user
                            )

                            newCommunity?.let {
                                _uiState.value =
                                    _uiState.value.copy(myCommunities = _uiState.value.myCommunities + newCommunity)
                            }
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

                val filteredCommunity = _uiState.value.communities.filter { it.community.id != communityId }
                val communityAsMember = _uiState.value.communities.first { it.community.id == communityId }

                _uiState.value = _uiState.value.copy(
                    joiningToCommunity = false,
                    communities = filteredCommunity,
                    newCommunity = communityAsMember,
                    communitiesUserIn = _uiState.value.communitiesUserIn + communityAsMember
                )
            }
        }
    }

    fun getCurrentCommunity(communityId: String): CommunityWithMembers? {
        println("_uiState.value.myCommunities: ${_uiState.value.myCommunities}")
        return _uiState.value.myCommunities.find {
            it.community.id == communityId
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
                repository.getAll()
                    .flowOn(Dispatchers.IO)
                    .collect { communities ->
                        val communityWithMembers = coroutineScope {
                            communities.map { community ->
                                async {
                                    val members = repository.getAllMembers(community.id)

                                    val finalMembers = if (members.isEmpty() &&
                                        community.email == user?.email) {
                                        delay(500)
                                        val retryMembers = repository.getAllMembers(community.id)
                                        retryMembers
                                    } else {
                                        members
                                    }

                                    CommunityWithMembers(community, finalMembers)
                                }
                            }.awaitAll()
                        }
                        println("communityWithMembers: ${communityWithMembers.size}")
                        _uiState.value = _uiState.value.copy(
                            communities = communityWithMembers.communitiesToJoin(user?.id.toString()),
                            myCommunities = communityWithMembers,
                            loadCommunities = false
                        )
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    private fun getAllCommunitiesUserIn() {
        viewModelScope.launch {
            val communities = repository.getCommunitiesUserIn()
            _uiState.value = _uiState.value.copy(
                communitiesUserIn = communities,
                loadingCommunitiesUserIn = false
            )
        }
    }
}