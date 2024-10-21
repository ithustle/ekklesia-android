package com.toquemedia.ekklesia.di

import android.content.Context
import com.toquemedia.ekklesia.dao.AppDatabase
import com.toquemedia.ekklesia.dao.BibleDao
import com.toquemedia.ekklesia.dao.DevocionalDao
import com.toquemedia.ekklesia.dao.NoteDao
import com.toquemedia.ekklesia.dao.VerseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideBibleDao(@ApplicationContext context: Context): BibleDao = BibleDao(context)
    @Provides
    fun provideVerseDao(@ApplicationContext context: Context): VerseDao = VerseDao(context)
    @Provides
    fun provideNoteDao(appDatabase: AppDatabase) : NoteDao = appDatabase.NoteDao()
    @Provides
    fun provideDevocionalDao(appDatabase: AppDatabase) : DevocionalDao = appDatabase.DevocionalDao()
}