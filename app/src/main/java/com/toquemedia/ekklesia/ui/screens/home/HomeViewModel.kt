package com.toquemedia.ekklesia.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.toquemedia.ekklesia.dao.LikeDao
import com.toquemedia.ekklesia.extension.convertToString
import com.toquemedia.ekklesia.extension.isInternetAvailable
import com.toquemedia.ekklesia.extension.toPortuguese
import com.toquemedia.ekklesia.model.VerseType
import com.toquemedia.ekklesia.repository.BibleRepositoryImpl
import com.toquemedia.ekklesia.repository.VerseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val verseRepository: VerseRepositoryImpl,
    private val bibleRepository: BibleRepositoryImpl,
    private val likeDao: LikeDao
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadingHome()
    }

    fun loadingHome() {
        if (context.isInternetAvailable()) {
            viewModelScope.launch {
                try {
                    getVerseOfDay()
                } catch (e: IOException) {
                    e.printStackTrace()
                    _uiState.update { it.copy(errorConnection = "Sem conexão a internet. Mensagem: ${e.localizedMessage}") }
                } catch (e: HttpException) {
                    e.printStackTrace()
                    _uiState.update { it.copy(errorConnection = "Erro no servidor") }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _uiState.update { it.copy(errorConnection = "Ocorreu um erro inesperado") }
                }
            }

            viewModelScope.launch {
                try {
                    likeDao.getLikes().collect {
                        _uiState.value = _uiState.value.copy(likedVerseOfDay = it.contains(Date().convertToString()))
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    _uiState.update { it.copy(errorConnection = "Sem conexão a internet. Mensagem: ${e.localizedMessage}") }
                } catch (e: HttpException) {
                    e.printStackTrace()
                    _uiState.update { it.copy(errorConnection = "Erro no servidor") }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _uiState.update { it.copy(errorConnection = "Ocorreu um erro inesperado") }
                }
            }
        } else {
            _uiState.update { it.copy(errorConnection = "Sem conexão a internet") }
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

    fun shareVerseOfDay() {
        viewModelScope.launch {
            val stats = verseRepository.shareVerseOfDay()
            _uiState.value = _uiState.value.copy(verseOfDayStats = stats)
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
                    text = verses?.get(res.verseOfDay.second - 1)?.get(res.verseOfDay.third - 1)
                        .toString()
                ),
                verseOfDayStats = res.stats,
                errorConnection = null,
            )
        }
    }
}