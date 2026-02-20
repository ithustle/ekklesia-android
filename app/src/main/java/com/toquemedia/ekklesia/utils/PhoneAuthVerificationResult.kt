package com.toquemedia.ekklesia.utils

import com.toquemedia.ekklesia.model.UserType

sealed class PhoneAuthVerificationResult {
    data class Success(val user: UserType) : PhoneAuthVerificationResult()
    data class NewUser(val userId: String) : PhoneAuthVerificationResult()
    data class Error(val message: String) : PhoneAuthVerificationResult()
}