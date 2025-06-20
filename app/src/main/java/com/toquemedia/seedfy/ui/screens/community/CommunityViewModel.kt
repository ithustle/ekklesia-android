package com.toquemedia.seedfy.ui.screens.community

import android.content.Context
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.extension.communitiesToJoin
import com.toquemedia.seedfy.extension.isInternetAvailable
import com.toquemedia.seedfy.model.CommunityMemberType
import com.toquemedia.seedfy.model.CommunityWithMembers
import com.toquemedia.seedfy.model.ValidationResult
import com.toquemedia.seedfy.repository.AuthRepositoryImpl
import com.toquemedia.seedfy.repository.CommunityRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    @ApplicationContext val context: Context,
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

        println("HOME VIEW MODEL")

        loadCommunities()
    }

    fun loadCommunities() {
        viewModelScope.launch {
            if (context.isInternetAvailable()) {
                val user = authRepository.getCurrentUser()
                if (user != null) {
                    try {
                        coroutineScope {
                            val communitiesDeferred = async { getAllCommunities() }
                            val communitiesInDeferred = async { getAllCommunitiesUserIn() }
                            communitiesInDeferred.await() to communitiesDeferred.await()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: HttpException) {
                        e.printStackTrace()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    _uiState.update { it.copy(loadingCommunitiesUserIn = false) }
                }
            } else {
                _uiState.update { it.copy(loadingCommunitiesUserIn = false) }
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
                                    _uiState.value.copy(
                                        myCommunities = _uiState.value.myCommunities + newCommunity,
                                    )
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

    fun selectCommunity(community: CommunityWithMembers) {
        _uiState.value = _uiState.value.copy(selectedCommunity = community)
    }

    fun joinToCommunity(communityId: String) {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()

            _uiState.value = _uiState.value.copy(joiningToCommunity = true)

            user?.let { user ->
                val member = CommunityMemberType(
                    id = user.id,
                    user = user,
                    admin = false
                )
                repository.addMember(
                    communityId = communityId,
                    member = member
                )

                val communities = _uiState.value.communities.toMutableList()
                val communityIndex = communities.indexOfFirst { it.community.id == communityId }

                if (communityIndex != -1) {
                    val updatedCommunity = communities[communityIndex].copy(
                        allMembers = communities[communityIndex].allMembers + member
                    )

                    communities[communityIndex] = updatedCommunity

                    val filteredCommunity =
                        communities.filter { it.community.id != communityId }
                    val communityAsMember = updatedCommunity

                    _uiState.value = _uiState.value.copy(
                        joiningToCommunity = false,
                        communities = filteredCommunity,
                        newCommunity = communityAsMember,
                        selectedCommunity = updatedCommunity,
                        communitiesUserIn = _uiState.value.communitiesUserIn + communityAsMember
                    )
                } else {
                    _uiState.value = _uiState.value.copy(joiningToCommunity = false)
                }
            }
        }
    }

    fun getCurrentCommunity(communityId: String): CommunityWithMembers? {
        return _uiState.value.myCommunities.find {
            it.community.id == communityId
        }
    }

    fun deleteCommunity(communityId: String) {
        viewModelScope.launch {
            repository.deleteCommunity(communityId)
        }
    }

    fun removeCommunityFromList(communityId: String, memberId: String) {
        val updatedCommunity = _uiState.value.communitiesUserIn.filter { it.community.id != communityId }
        val communityToRemoveMember = _uiState.value.communitiesUserIn.first { it.community.id == communityId }
        val communityWithoutMember =
            communityToRemoveMember.copy(allMembers = communityToRemoveMember.allMembers.filter { it.user.email != memberId })

        _uiState.update {
            it.copy(
                communitiesUserIn = updatedCommunity,
                communities = _uiState.value.communities + communityWithoutMember,
                loadingLeftCommunity = false
            )
        }
    }

    suspend fun removeMember(communityId: String, memberId: String) {
        try {
            _uiState.update { it.copy(loadingLeftCommunity = true) }
            repository.removeMember(communityId, memberId)

            val communityLeft = _uiState.value.communities.firstOrNull { it.community.id == communityId }
                ?: return

            val communityWithoutMemberLeft =
                communityLeft.copy(allMembers = communityLeft.allMembers.filter { it.id != memberId })

            val updatedCommunitiesList = _uiState.value.communities.map { community ->
                if (community.community.id == communityId) communityWithoutMemberLeft else community
            }

            val updatedCommunity = _uiState.value.communitiesUserIn.filter { it.community.id != communityId }

            _uiState.update {
                it.copy(
                    loadingLeftCommunity = false,
                    communities = updatedCommunitiesList,
                    communitiesUserIn = updatedCommunity
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _uiState.update { it.copy(loadingLeftCommunity = false) }
        }
    }

    suspend fun leftCommunity(communityId: String) {
        try {
            this.removeMember(communityId.toString(), user?.id.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                context,
                context.getString(R.string.message_error_left_community),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private suspend fun getAllCommunities() {
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
                                    community.email == user?.email
                                ) {
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
                    _uiState.value = _uiState.value.copy(
                        communities = communityWithMembers.communitiesToJoin(user?.id.toString()),
                        myCommunities = communityWithMembers.filter { it.community.email == user?.email },
                        loadCommunities = false
                    )
                }
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
        }
    }

    private fun getAllCommunitiesUserIn() {
        try {
            viewModelScope.launch {
                val communities = repository.getCommunitiesUserIn()
                _uiState.value = _uiState.value.copy(
                    communitiesUserIn = communities,
                    loadingCommunitiesUserIn = false,
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}