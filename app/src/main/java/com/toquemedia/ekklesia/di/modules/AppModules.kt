package com.toquemedia.ekklesia.di.modules

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.toquemedia.ekklesia.dao.AppCacheDao
import com.toquemedia.ekklesia.dao.AppDatabase
import com.toquemedia.ekklesia.dao.LikeDao
import com.toquemedia.ekklesia.dao.VerseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Named
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

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun provideAuthEmulator(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    @Named("ourmannaService")
    fun provideRetrofitOurmannaService(): Retrofit = Retrofit.Builder()
        .baseUrl("https://beta.ourmanna.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @Named("bunnyService")
    fun provideRetrofitBunnyService(): Retrofit = Retrofit.Builder()
        .baseUrl("https://video.bunnycdn.com/library/415301/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}