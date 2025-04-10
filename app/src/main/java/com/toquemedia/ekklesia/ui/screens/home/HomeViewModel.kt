package com.toquemedia.ekklesia.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toquemedia.ekklesia.dao.LikeDao
import com.toquemedia.ekklesia.extension.communitiesToJoin
import com.toquemedia.ekklesia.extension.convertToString
import com.toquemedia.ekklesia.extension.toPortuguese
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.VerseType
import com.toquemedia.ekklesia.repository.AuthRepositoryImpl
import com.toquemedia.ekklesia.repository.BibleRepositoryImpl
import com.toquemedia.ekklesia.repository.CommunityRepositoryImpl
import com.toquemedia.ekklesia.repository.VerseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val verseRepository: VerseRepositoryImpl,
    private val communityRepository: CommunityRepositoryImpl,
    private val bibleRepository: BibleRepositoryImpl,
    private val authRepository: AuthRepositoryImpl,
    private val likeDao: LikeDao
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

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

        viewModelScope.launch {
            likeDao.getLikes().collect {
                _uiState.value = _uiState.value.copy(likedVerseOfDay = it.contains(Date().convertToString()))
            }
        }

        viewModelScope.launch {
            getAllCommunitiesUserIn()
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

    fun handleLikeVerseOfDay(isForLike: Boolean) {
        viewModelScope.launch {
            try {
                if (isForLike) {
                    likeDao.removeLikeRegister(Date().convertToString())
                } else {
                    likeDao.saveLikeRegister(Date().convertToString())
                }

                withContext(Dispatchers.IO) {
                    val stats = verseRepository.handleLikeVerseOfDay(isForLike)
                    _uiState.value = _uiState.value.copy(verseOfDayStats = stats)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                likeDao.removeLikeRegister(Date().convertToString())
            }
        }
    }

    private suspend fun getVerseOfDay() {
        val result = verseRepository.getVerseOfDay()
        val books = bibleRepository.getBooks()

        result?.let { res ->
            val verses = books.find { it.bookName == res.verseOfDay.first.toPortuguese() }?.verses

            _uiState.value = _uiState.value.copy(
                verseOfDay = VerseType(
                    bookName = res.verseOfDay.first.toPortuguese(),
                    chapter = res.verseOfDay.second,
                    versicle = res.verseOfDay.third,
                    text = verses?.get(res.verseOfDay.second - 1)?.get(res.verseOfDay.third - 1).toString()
                ),
                verseOfDayStats = res.stats
            )
        }
    }

    private fun getAllCommunities() {
        viewModelScope.launch {
            try {
                val user = authRepository.getCurrentUser()
                user?.email?.let {
                    val communities = communityRepository.getAll(it)

                    withContext(Dispatchers.IO) {
                        val all = communities.map { community ->
                            async {
                                val members = communityRepository.getAllMembers(community.id)
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
            communityRepository.getCommunitiesUserInIds().collect {
                val communities = communityRepository.getCommunitiesUserIn(it)
                _uiState.value = _uiState.value.copy(communitiesUserIn = communities, loadingCommunitiesUserIn = false)
            }
        }
    }
}