package com.toquemedia.seedfy.model

data class BibleType(
    val abbrev: String,
    val chapters: List<List<String>> = emptyList(),
    val name: String
) {

}
