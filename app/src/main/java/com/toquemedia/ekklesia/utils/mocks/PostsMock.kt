package com.toquemedia.ekklesia.utils.mocks

import androidx.core.net.toUri
import com.toquemedia.ekklesia.model.CommentType
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
                createdAt = Date(),
                comments = listOf(
                    CommentType(
                        user = UserType(
                            id = "PcnbUv0JJmQoneWrV2FJ3U4aVAO2",
                            displayName = "Kwanza Online - Célio Garcia",
                            email = "kwanzaonline@gmail.com",
                            photo = "https://lh3.googleusercontent.com/a/ACg8ocLT355LIkNXIcV1OPzHC77vK58LSav0DkGwXgimpb0SjcJi3g=s96-c"

                        ), comment = "Amém",
                        likes = 0,
                        createdAt = Date(),
                        id = "107d5865-fa65-4687-baca-26f500a31cd4"
                    )
                )
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