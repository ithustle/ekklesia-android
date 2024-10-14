package com.toquemedia.ekklesia.model

data class BibleType(
    val abbrev: String,
    val chapters: List<List<String>> = emptyList(),
    val name: String
) {

}
