package com.toquemedia.ekklesia.di.modules

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toquemedia.ekklesia.dao.AppCacheDao
import com.toquemedia.ekklesia.dao.AppDatabase
import com.toquemedia.ekklesia.dao.BibleDao
import com.toquemedia.ekklesia.dao.CommunityDao
import com.toquemedia.ekklesia.dao.DevocionalDao
import com.toquemedia.ekklesia.dao.MessageDao
import com.toquemedia.ekklesia.dao.NoteDao
import com.toquemedia.ekklesia.dao.VerseDao
import com.toquemedia.ekklesia.services.CommunityService
import com.toquemedia.ekklesia.services.NoteService
import com.toquemedia.ekklesia.services.OurmannaService
import com.toquemedia.ekklesia.services.PostService
import com.toquemedia.ekklesia.services.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

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
    @Provides
    fun provideNoteService(): NoteService = NoteService()
    @Provides
    fun provideCommunityDao(appDatabase: AppDatabase): CommunityDao = appDatabase.CommunityDao()
    @Provides
    fun provideMessageDao(appDatabase: AppDatabase): MessageDao = appDatabase.MessageDao()
    @Provides
    fun provideCommunityService(): CommunityService = CommunityService()
    @Provides
    fun provideOurmannaService(retrofit: Retrofit) : OurmannaService = retrofit.create(
        OurmannaService::class.java)
    @Provides
    fun provideAppCacheDao(@ApplicationContext context: Context): AppCacheDao = AppCacheDao(context)
    @Provides
    fun providePostService(firestore: FirebaseFirestore): PostService = PostService(firestore)
    @Provides
    fun provideUserService(@ApplicationContext context: Context, auth: FirebaseAuth): UserService = UserService(context, auth)
}