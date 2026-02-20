package com.toquemedia.ekklesia.model

sealed class ValidationResult {
    data object Success : ValidationResult()
    data object Loading : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}