package com.toquemedia.ekklesia.utils.mocks

import com.toquemedia.ekklesia.model.NoteEntity
import com.toquemedia.ekklesia.model.PostType
import com.toquemedia.ekklesia.model.UserType
import java.util.Date

object PostsMock {

    fun getPosts(): List<PostType> {
        return listOf(
            PostType(
                verse = "Bem sei que o meu redentor vive, e que no fim do mundo, sobre a terra, verá o meu corpo",
                user = UserType(
                    id = "1",
                    displayName = "Elsa Tomás",
                    photo = "https://picsum.photos/200/300"
                ),
                verseId = "Jó_19_25_1",
                createdAt = Date()
            ),
            PostType(
                note = NoteEntity(
                    bookName = "Mateus",
                    chapter = 6,
                    versicle = 12,
                    verse = "Perdoem as nossas dívidas, assim como perdoamos os nossos devedores",
                    note = "Minha nota sobre o versículo",
                    id = "1"
                ),
                verse = "Bem sei que o meu redentor vive, e que no fim do mundo, sobre a terra, verá o meu corpo",
                user = UserType(
                    id = "1",
                    displayName = "Elsa Tomás",
                    photo = "https://picsum.photos/200/300"
                ),
                verseId = "Jó_19_25_1",
                createdAt = Date()
            )
        )
    }
}