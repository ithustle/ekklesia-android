package com.toquemedia.ekklesia.di.modules

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.toquemedia.ekklesia.dao.AppDatabase
import com.toquemedia.ekklesia.dao.BibleDao
import com.toquemedia.ekklesia.dao.DevocionalDao
import com.toquemedia.ekklesia.dao.LikeDao
import com.toquemedia.ekklesia.dao.MessageDao
import com.toquemedia.ekklesia.dao.NoteDao
import com.toquemedia.ekklesia.services.CommunityService
import com.toquemedia.ekklesia.services.NoteService
import com.toquemedia.ekklesia.services.OurmannaService
import com.toquemedia.ekklesia.services.PostService
import com.toquemedia.ekklesia.services.StorageService
import com.toquemedia.ekklesia.services.UserService
import com.toquemedia.ekklesia.services.VerseOfDayService
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
    fun provideNoteDao(appDatabase: AppDatabase) : NoteDao = appDatabase.NoteDao()
    @Provides
    fun provideDevocionalDao(appDatabase: AppDatabase) : DevocionalDao = appDatabase.DevocionalDao()
    @Provides
    fun provideMessageDao(appDatabase: AppDatabase): MessageDao = appDatabase.MessageDao()
    @Provides
    fun provideCommunityService(userService: UserService, firestore: FirebaseFirestore): CommunityService = CommunityService(auth = userService, db = firestore)
    @Provides
    fun provideNoteService(): NoteService = NoteService()
    @Provides
    fun provideStorageService(@ApplicationContext context: Context, storage: FirebaseStorage): StorageService = StorageService(context, storage)
    @Provides
    fun provideVerseOfDayService(firestore: FirebaseFirestore): VerseOfDayService = VerseOfDayService(firestore)
    @Provides
    fun provideOurmannaService(retrofit: Retrofit) : OurmannaService = retrofit.create(
        OurmannaService::class.java)
    @Provides
    fun providePostService(firestore: FirebaseFirestore): PostService = PostService(firestore)
    @Provides
    fun provideUserService(@ApplicationContext context: Context, firestore: FirebaseFirestore, auth: FirebaseAuth, dao: LikeDao): UserService = UserService(context, firestore, auth, dao)
}