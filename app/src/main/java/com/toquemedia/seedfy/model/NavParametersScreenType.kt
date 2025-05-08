package com.toquemedia.seedfy.model

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

inline fun <reified T : Parcelable> createNavParametersScreenType() = object : NavType<T>(
    isNullableAllowed = false
) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun get(
        bundle: Bundle,
        key: String
    ): T? {
        return bundle.getParcelable(key, T::class.java)
    }

    override fun parseValue(value: String): T {
        return Json.decodeFromString<T>(value)
    }

    override fun put(
        bundle: Bundle,
        key: String,
        value: T
    ) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: T): String {
        return Json.encodeToString(value)
    }
}