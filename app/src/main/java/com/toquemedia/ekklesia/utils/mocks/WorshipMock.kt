package com.toquemedia.ekklesia.utils.mocks

import com.toquemedia.ekklesia.model.WorshipEntity
import java.util.UUID

object WorshipMock {
    fun getAll(): List<WorshipEntity> {
        return listOf(
            WorshipEntity(
                id = UUID.randomUUID().toString(),
                bookName = "Salmos",
                chapter = 23,
                versicle = 1,
                verse = "O Senhor é meu pastor, nada me faltará.",
                title = "O Bom Pastor",
                backgroundColor = "Color(0.19215687, 0.101960786, 1.0, 1.0, sRGB IEC61966-2.1)",
                worship = "Este salmo nos lembra que Deus cuida de nós como um pastor cuida de suas ovelhas...",
                video = "https://example.com/videos/salmo23.mp4",
                durationVideo = 78
            ),
            WorshipEntity(
                id = UUID.randomUUID().toString(),
                bookName = "João",
                chapter = 3,
                versicle = 16,
                verse = "Porque Deus amou o mundo de tal maneira que deu o seu Filho unigênito...",
                title = "O Amor de Deus",
                worship = "Este versículo nos mostra o imenso amor que Deus tem pela humanidade...",
                video = "https://example.com/videos/joao316.mp4",
                durationVideo = 45
            ),
            WorshipEntity(
                id = UUID.randomUUID().toString(),
                bookName = "Filipenses",
                chapter = 4,
                versicle = 13,
                verse = "Tudo posso naquele que me fortalece.",
                title = "Força em Cristo",
                worship = "Paulo nos ensina que através de Cristo podemos superar qualquer desafio...",
                video = "https://example.com/videos/filipenses413.mp4",
                durationVideo = 196
            )
        )
    }
}