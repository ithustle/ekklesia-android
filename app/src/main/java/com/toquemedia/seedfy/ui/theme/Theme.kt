package com.toquemedia.seedfy.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val OnPrimaryDark = Color.Black // Texto/ícones sobre a cor primária no modo escuro
val PrimaryContainerDark = Color(0xFF3700B3) // Um tom para containers relacionados à cor primária
val OnPrimaryContainerDark = Color.White
val SecondaryDark = Color(0xFF03DAC6) // Cor secundária
val OnSecondaryDark = Color.Black
val SecondaryContainerDark = Color(0xFF018786)
val OnSecondaryContainerDark = Color.White
val TertiaryDark = Color(0xFFBB86FC) // Cor terciária
val OnTertiaryDark = Color.Black
val TertiaryContainerDark = Color(0xFF3700B3)
val OnTertiaryContainerDark = Color.White
val OnBackgroundDark = Color.White // Texto/ícones sobre a cor de fundo principal
val SurfaceDark = Color(0xFF1E1E1E) // Cor para superfícies de cards, sheets, menus
val OnSurfaceDark = Color.White // Texto/ícones sobre as superfícies
val SurfaceVariantDark = Color(0xFF303030) // Outra variante de superfície
val OnSurfaceVariantDark = Color(0xFFCACACA)
val ErrorDark = Color(0xFFCF6679) // Cor para erros
val OnErrorDark = Color.Black
val OutlineDark = Color(0xFF8A8A8A) // Cor para contornos

// Cores para o tema claro
val OnPrimaryLight = Color.White // Texto/ícones sobre a cor primária no modo claro
val PrimaryContainerLight = Color(0xFFBB86FC) // Um tom para containers relacionados à cor primária
val OnPrimaryContainerLight = Color.Black
val SecondaryLight = Color(0xFF03DAC5) // Cor secundária
val OnSecondaryLight = Color.Black
val SecondaryContainerLight = Color(0xFF018786)
val OnSecondaryContainerLight = Color.White
val TertiaryLight = Color(0xFF3700B3) // Cor terciária
val OnTertiaryLight = Color.White
val TertiaryContainerLight = Color(0xFF3700B3) // Exemplo
val OnTertiaryContainerLight = Color.White // Exemplo
val OnBackgroundLight = Color.Black // Texto/ícones sobre a cor de fundo principal
val SurfaceLight = Color(0xFFF5F5F5) // Cor para superfícies
val OnSurfaceLight = Color.Black // Texto/ícones sobre as superfícies
val SurfaceVariantLight = Color(0xFFE0E0E0) // Outra variante de superfície
val OnSurfaceVariantLight = Color.Black
val ErrorLight = Color(0xFFB00020) // Cor para erros
val OnErrorLight = Color.White
val OutlineLight = Color(0xFF757575) // Cor para contornos

private val DarkColorScheme = darkColorScheme(
    primary = PrincipalColor,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,
    background = BackgroundDarkColor,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    error = ErrorDark,
    onError = OnErrorDark,
    outline = OutlineDark,
    // scrim é geralmente usado para escurecer o fundo atrás de dialogs/drawers
    scrim = Color.Black.copy(alpha = 0.6f) // Pode ajustar a opacidade conforme necessário
)

private val LightColorScheme = lightColorScheme(
    primary = PrincipalColor,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,
    background = BackgroundLightColor,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight, // Você já tinha, mas verifique a cor OnSurfaceVariantLight
    onSurfaceVariant = OnSurfaceVariantLight,
    error = ErrorLight,
    onError = OnErrorLight,
    outline = OutlineLight,
    scrim = Color.Black.copy(alpha = 0.5f) // Você já tinha
)
@Composable
fun EkklesiaTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}