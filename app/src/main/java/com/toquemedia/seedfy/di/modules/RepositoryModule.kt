package com.toquemedia.seedfy.di.modules

import com.toquemedia.seedfy.model.interfaces.AuthRepository
import com.toquemedia.seedfy.model.interfaces.BibleRepository
import com.toquemedia.seedfy.model.interfaces.CommunityRepository
import com.toquemedia.seedfy.model.interfaces.MessageRepository
import com.toquemedia.seedfy.model.interfaces.NoteRepository
import com.toquemedia.seedfy.model.interfaces.PostRepository
import com.toquemedia.seedfy.model.interfaces.VerseRepository
import com.toquemedia.seedfy.model.interfaces.WorshipRepository
import com.toquemedia.seedfy.repository.AuthRepositoryImpl
import com.toquemedia.seedfy.repository.BibleRepositoryImpl
import com.toquemedia.seedfy.repository.CommunityRepositoryImpl
import com.toquemedia.seedfy.repository.MessageRepositoryImpl
import com.toquemedia.seedfy.repository.NoteRepositoryImpl
import com.toquemedia.seedfy.repository.PostRepositoryImpl
import com.toquemedia.seedfy.repository.VerseRepositoryImpl
import com.toquemedia.seedfy.repository.WorshipRepositoryImpl
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
    abstract fun bindWorshipRepository(
        impl: WorshipRepositoryImpl
    ) : WorshipRepository

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

    @Binds
    @Singleton
    abstract fun bindMessageRepository(
        impl: MessageRepositoryImpl
    ) : MessageRepository

    @Binds
    @Singleton
    abstract fun bindPostRepository(
        impl: PostRepositoryImpl
    ): PostRepository
}