package com.toquemedia.ekklesia.model

sealed class PostType {
    data class Note(val note: NoteEntity) : PostType()
    class Verse(val verse: String) : PostType()
}