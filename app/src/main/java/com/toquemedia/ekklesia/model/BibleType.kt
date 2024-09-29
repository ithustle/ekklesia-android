package com.toquemedia.ekklesia.model

data class BibleType(
    val abbrev: String,
    val chapters: Array<Array<String>> = arrayOf(),
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BibleType

        if (abbrev != other.abbrev) return false
        if (!chapters.contentDeepEquals(other.chapters)) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = abbrev.hashCode()
        result = 31 * result + chapters.contentDeepHashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}
