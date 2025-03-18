package com.toquemedia.ekklesia.di.modules

import com.toquemedia.ekklesia.model.interfaces.AuthRepository
import com.toquemedia.ekklesia.model.interfaces.BibleRepository
import com.toquemedia.ekklesia.model.interfaces.CommunityRepository
import com.toquemedia.ekklesia.model.interfaces.DevocionalRepository
import com.toquemedia.ekklesia.model.interfaces.NoteRepository
import com.toquemedia.ekklesia.model.interfaces.VerseRepository
import com.toquemedia.ekklesia.repository.AuthRepositoryImpl
import com.toquemedia.ekklesia.repository.BibleRepositoryImpl
import com.toquemedia.ekklesia.repository.CommunityRepositoryImpl
import com.toquemedia.ekklesia.repository.DevocionalRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindDevocionalRepository(
        impl: DevocionalRepositoryImpl
    ) : DevocionalRepository

    @Binds
    @Singleton
    abstract fun bindCommunityRepository(
        impl: CommunityRepositoryImpl
    ) : CommunityRepository

    @Binds
    @Singleton
    abstract fun bindAuthServiceRepository(
        impl: AuthRepositoryImpl
    ) : AuthRepository
}