package com.toquemedia.ekklesia.di

import android.content.Context
import com.toquemedia.ekklesia.model.BibleRepository
import com.toquemedia.ekklesia.model.VerseRepository
import com.toquemedia.ekklesia.repository.BibleRepositoryImpl
import com.toquemedia.ekklesia.repository.VerseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBibleRepository(
        impl: BibleRepositoryImpl,
    ) : BibleRepository

    @Binds
    @Singleton
    abstract fun bindVerseRepository(
        impl: VerseRepositoryImpl
    ) : VerseRepository
}