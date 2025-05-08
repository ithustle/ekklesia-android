package com.toquemedia.seedfy.dao

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.toquemedia.seedfy.model.BibleType

class BibleDao(private val context: Context) {

    fun loadFileBible(): List<BibleType> {
        return context.assets.open("pt_nvi.json")
            .bufferedReader().use {
                this.parseJson(it.readText())
            }
    }

    private fun parseJson(value: String): List<BibleType> {
        val gson = Gson()
        val bibleType = object : TypeToken<List<BibleType>>() {}.type
        return gson.fromJson(value, bibleType)
    }
}