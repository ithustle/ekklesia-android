package com.toquemedia.ekklesia.model.interfaces

import com.toquemedia.ekklesia.model.UserType

interface AuthRepository {
    suspend fun googleSignIn(): UserType?
    fun getCurrentUser(): UserType?
    suspend fun signOut()
}