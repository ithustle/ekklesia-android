package com.toquemedia.ekklesia.di

import com.toquemedia.ekklesia.model.interfaces.BibleRepository
import com.toquemedia.ekklesia.model.interfaces.NoteRepository
import com.toquemedia.ekklesia.model.interfaces.VerseRepository
import com.toquemedia.ekklesia.repository.BibleRepositoryImpl
import com.toquemedia.ekklesia.repository.NoteRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        impl: NoteRepositoryImpl
    ) : NoteRepository
}