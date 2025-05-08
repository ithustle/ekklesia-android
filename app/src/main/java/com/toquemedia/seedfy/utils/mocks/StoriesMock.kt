package com.toquemedia.seedfy.utils.mocks

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.toquemedia.seedfy.model.StoryType
import com.toquemedia.seedfy.model.UserType
import java.util.Date

object StoriesMock {
    fun getAll(): List<StoryType> {
        return listOf(
            StoryType(
                createdAt = Date(),
                expirationDate = Date().apply { time += 24 * 60 * 60 * 1000 },
                verse = "Porque Deus amou o mundo de tal maneira...",
                user = UserType("user1", "João Silva", email = "kwanzaonline@gmail.com"),
                bookNameWithVersicle = "João 3:16",
                backgroundColor = Color(0xFF6200EE).toArgb(),
                verseColor = Color(0xFFFFFFFF).toArgb(),
                communityId = listOf("comm1", "comm2")
            ),
            StoryType(
                createdAt = Date(),
                expirationDate = Date().apply { time += 24 * 60 * 60 * 1000 },
                verse = "O Senhor é meu pastor, nada me faltará.",
                user = UserType("user2", "Maria Oliveira", email = "kwanzaonline@gmail.com"),
                bookNameWithVersicle = "Salmos 23:1",
                backgroundColor = Color(0xFF03DAC5).toArgb(),
                verseColor = Color(0xFF000000).toArgb(),
                communityId = listOf("comm3")
            ),
            StoryType(
                createdAt = Date(),
                expirationDate = Date().apply { time += 24 * 60 * 60 * 1000 },
                verse = "Tudo posso naquele que me fortalece.",
                user = UserType("user3", "Pedro Santos", email = "kwanzaonline2016@gmail.com"),
                bookNameWithVersicle = "Filipenses 4:13",
                backgroundColor = Color(0xFFFF5722).toArgb(),
                verseColor = Color(0xFFFFFFFF).toArgb(),
                communityId = emptyList()
            ),
            StoryType(
                createdAt = Date(),
                expirationDate = Date().apply { time += 24 * 60 * 60 * 1000 },
                verse = "Confia no Senhor de todo o teu coração...",
                user = UserType("user4", "Ana Costa", email = "kwanzaonline2016@gmail.com"),
                bookNameWithVersicle = "Provérbios 3:5",
                backgroundColor = Color(0xFF2196F3).toArgb(),
                verseColor = Color(0xFF000000).toArgb(),
                communityId = listOf("comm4", "comm5")
            ),
            StoryType(
                createdAt = Date(),
                expirationDate = Date().apply { time += 24 * 60 * 60 * 1000 },
                verse = "Alegrai-vos sempre no Senhor...",
                user = UserType("user5", "Lucas Almeida"),
                bookNameWithVersicle = "Filipenses 4:4",
                backgroundColor = Color(0xFFFFC107).toArgb(),
                verseColor = Color(0xFF000000).toArgb(),
                communityId = listOf("comm1")
            ),
            StoryType(
                createdAt = Date(),
                expirationDate = Date().apply { time += 24 * 60 * 60 * 1000 },
                verse = "Se Deus é por nós, quem será contra nós?",
                user = UserType("user6", "Clara Mendes"),
                bookNameWithVersicle = "Romanos 8:31",
                backgroundColor = Color(0xFF4CAF50).toArgb(),
                verseColor = Color(0xFFFFFFFF).toArgb(),
                communityId = listOf("comm2", "comm6")
            ),
            StoryType(
                createdAt = Date(),
                expirationDate = Date().apply { time += 24 * 60 * 60 * 1000 },
                verse = "Bem-aventurados os humildes de espírito...",
                user = UserType("user7", "Rafael Lima"),
                bookNameWithVersicle = "Mateus 5:3",
                backgroundColor = Color(0xFF9C27B0).toArgb(),
                verseColor = Color(0xFFFFFFFF).toArgb(),
                communityId = emptyList()
            ),
            StoryType(
                createdAt = Date(),
                expirationDate = Date().apply { time += 24 * 60 * 60 * 1000 },
                verse = "O amor é paciente, o amor é bondoso...",
                user = UserType("user8", "Sofia Pereira"),
                bookNameWithVersicle = "1 Coríntios 13:4",
                backgroundColor = Color(0xFFE91E63).toArgb(),
                verseColor = Color(0xFF000000).toArgb(),
                communityId = listOf("comm7")
            ),
            StoryType(
                createdAt = Date(),
                expirationDate = Date().apply { time += 24 * 60 * 60 * 1000 },
                verse = "Buscai primeiro o reino de Deus...",
                user = UserType("user9", "Gabriel Ferreira"),
                bookNameWithVersicle = "Mateus 6:33",
                backgroundColor = Color(0xFF009688).toArgb(),
                verseColor = Color(0xFFFFFFFF).toArgb(),
                communityId = listOf("comm8")
            ),
            StoryType(
                createdAt = Date(),
                expirationDate = Date().apply { time += 24 * 60 * 60 * 1000 },
                verse = "Eu sou o caminho, a verdade e a vida...",
                user = UserType("user10", "Beatriz Rocha"),
                bookNameWithVersicle = "João 14:6",
                backgroundColor = Color(0xFF607D8B).toArgb(),
                verseColor = Color(0xFF000000).toArgb(),
                communityId = listOf("comm9", "comm10")
            )
        )
    }
}