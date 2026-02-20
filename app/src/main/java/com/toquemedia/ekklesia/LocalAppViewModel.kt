package com.toquemedia.ekklesia

import androidx.compose.runtime.compositionLocalOf

val LocalAppViewModel = compositionLocalOf<AppViewModel> { error("AppViewModel não fornecido") }