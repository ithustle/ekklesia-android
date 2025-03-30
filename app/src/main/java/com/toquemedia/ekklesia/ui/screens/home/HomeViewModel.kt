package com.toquemedia.ekklesia.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.extension.communitiesToJoin
import com.toquemedia.ekklesia.extension.toPortuguese
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.VerseType
import com.toquemedia.ekklesia.repository.AuthRepositoryImpl
import com.toquemedia.ekklesia.repository.BibleRepositoryImpl
import com.toquemedia.ekklesia.repository.CommunityRepositoryImpl
import com.toquemedia.ekklesia.repository.VerseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val verseRepository: VerseRepositoryImpl,
    private val communityRepository: CommunityRepositoryImpl,
    private val bibleRepository: BibleRepositoryImpl,
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val (verseOfDay, communities) = coroutineScope {
                    val verseDeferred = async { getVerseOfDay() }
                    val communitiesDeferred = async { getAllCommunities() }
                    verseDeferred.await() to communitiesDeferred.await()
                }
            } catch (e: Exception) {
                e.printStackTrace()
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
                communityRepository.addMember(
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

    private suspend fun getVerseOfDay() {
        val result = verseRepository.getVerseOfDay()
        val books = bibleRepository.getBooks()

        result?.let { res ->
            val verses = books.find { it.bookName == res.first.toPortuguese() }?.verses

            _uiState.value = _uiState.value.copy(
                verseOfDay = VerseType(
                    bookName = result.first.toPortuguese(),
                    chapter = result.second,
                    versicle = result.third,
                    text = verses?.get(res.second - 1)?.get(res.third - 1).toString()
                )
            )
        }
    }

    private fun getAllCommunities() {
        viewModelScope.launch {
            try {
                val user = authRepository.getCurrentUser()
                user?.email?.let {
                    val communities = communityRepository.getAll(it)
                    val all = communities.map { community ->
                        async {
                            val members = communityRepository.getAllMembers(community.id)
                            CommunityWithMembers(community, members)
                        }
                    }.awaitAll()

                    _uiState.value = _uiState.value.copy(communities = all.communitiesToJoin(user.id), loadCommunities = false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }
}