package com.toquemedia.seedfy

import androidx.compose.runtime.compositionLocalOf

val LocalAppViewModel = compositionLocalOf<AppViewModel> { error("AppViewModel não fornecido") }