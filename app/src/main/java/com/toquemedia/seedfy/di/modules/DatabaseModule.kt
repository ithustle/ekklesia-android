package com.toquemedia.seedfy.di.modules

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.toquemedia.seedfy.dao.AppDatabase
import com.toquemedia.seedfy.dao.BibleDao
import com.toquemedia.seedfy.dao.LikeDao
import com.toquemedia.seedfy.dao.MessageDao
import com.toquemedia.seedfy.dao.NoteDao
import com.toquemedia.seedfy.dao.WorshipDao
import com.toquemedia.seedfy.model.EkklesiaPlayer
import com.toquemedia.seedfy.services.BunnyService
import com.toquemedia.seedfy.services.CommunityService
import com.toquemedia.seedfy.services.NoteService
import com.toquemedia.seedfy.services.NotificationService
import com.toquemedia.seedfy.services.OurmannaService
import com.toquemedia.seedfy.services.PostService
import com.toquemedia.seedfy.services.StorageService
import com.toquemedia.seedfy.services.StoryService
import com.toquemedia.seedfy.services.UserService
import com.toquemedia.seedfy.services.VerseOfDayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Named
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideBibleDao(@ApplicationContext context: Context): BibleDao = BibleDao(context)
    @Provides
    fun provideNoteDao(appDatabase: AppDatabase) : NoteDao = appDatabase.NoteDao()
    @Provides
    fun provideWorshipDao(appDatabase: AppDatabase) : WorshipDao = appDatabase.WorshipDao()
    @Provides
    fun provideMessageDao(appDatabase: AppDatabase): MessageDao = appDatabase.MessageDao()
    @Provides
    fun provideCommunityService(userService: UserService, firestore: FirebaseFirestore): CommunityService = CommunityService(auth = userService, db = firestore)
    @Provides
    fun provideNoteService(firestore: FirebaseFirestore): NoteService = NoteService(firestore)
    @Provides
    fun provideStorageService(@ApplicationContext context: Context, storage: FirebaseStorage): StorageService = StorageService(context, storage)
    @Provides
    fun provideNotificationService(messaging: FirebaseMessaging): NotificationService = NotificationService(messaging)
    @Provides
    fun provideVerseOfDayService(firestore: FirebaseFirestore): VerseOfDayService = VerseOfDayService(firestore)

    @OptIn(UnstableApi::class)
    @Provides
    fun provideEkklesiaPlayer(@ApplicationContext context: Context): EkklesiaPlayer = EkklesiaPlayer(context)

    @Provides
    fun provideOurmannaService(@Named("ourmannaService") retrofit: Retrofit) : OurmannaService = retrofit.create(OurmannaService::class.java)

    @Provides
    fun provideBunnyService(@Named("bunnyService") retrofit: Retrofit): BunnyService = retrofit.create(BunnyService::class.java)

    @Provides
    fun providePostService(firestore: FirebaseFirestore): PostService = PostService(firestore)
    @Provides
    fun provideUserService(@ApplicationContext context: Context, firestore: FirebaseFirestore, auth: FirebaseAuth, dao: LikeDao): UserService = UserService(context, firestore, auth, dao)
    @Provides
    fun provideStoryService(firestore: FirebaseFirestore): StoryService = StoryService(firestore)
}