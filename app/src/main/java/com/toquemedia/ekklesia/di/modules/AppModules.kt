package com.toquemedia.ekklesia.di.modules

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.toquemedia.ekklesia.dao.AppCacheDao
import com.toquemedia.ekklesia.dao.AppDatabase
import com.toquemedia.ekklesia.dao.CommunityInsiderDao
import com.toquemedia.ekklesia.dao.LikeDao
import com.toquemedia.ekklesia.dao.VerseDao
import com.toquemedia.ekklesia.model.interfaces.EkklesiaDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java, "ekklesiaDb"
    ).build()

    @Singleton
    @Provides
    fun provideVersesDataStore(@ApplicationContext context: Context): LikeDao {
        return LikeDao(context)
    }

    @Singleton
    @Provides
    fun provideCommunitiesDataStore(@ApplicationContext context: Context): CommunityInsiderDao {
        return CommunityInsiderDao(context)
    }

    @Singleton
    @Provides
    fun provideUserDataStore(@ApplicationContext context: Context): VerseDao {
        return VerseDao(context)
    }

    @Singleton
    @Provides
    fun provideAppCacheDao(@ApplicationContext context: Context): AppCacheDao = AppCacheDao(context)

    @Provides
    @Singleton
    fun provideFirestoreDatabase(): FirebaseFirestore = FirebaseFirestore.getInstance().apply {
        firestoreSettings =
            FirebaseFirestoreSettings.Builder().build()
    }

    @Singleton
    @Provides
    fun provideAuthEmulator(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideRetrofitService(): Retrofit = Retrofit.Builder()
        .baseUrl("https://beta.ourmanna.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}