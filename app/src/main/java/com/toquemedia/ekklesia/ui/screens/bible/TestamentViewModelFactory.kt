package com.toquemedia.ekklesia.ui.screens.bible

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TestamentViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestamentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TestamentViewModel(context = context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}