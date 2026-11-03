package com.example.ui.theme

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

private val PrepSureLightColorScheme = lightColorScheme(
    primary = PrepSureBluePrimary,
    onPrimary = Color.White,
    primaryContainer = PrepSureBlueLight,
    onPrimaryContainer = PrepSureBlueDark,
    secondary = PrepSureGreen,
    onSecondary = Color.White,
    secondaryContainer = PrepSureGreenLight,
    onSecondaryContainer = Color(0xFF064E3B),
    tertiary = PrepSureTeal,
    onTertiary = Color.White,
    background = PrepSureBackgroundLight,
    onBackground = PrepSureTextPrimary,
    surface = PrepSureSurfaceLight,
    onSurface = PrepSureTextPrimary,
    surfaceVariant = PrepSureSurfaceVariant,
    onSurfaceVariant = PrepSureTextSecondary,
    outline = PrepSureBorder,
    error = PrepSureUrgentRed,
    errorContainer = PrepSureUrgentRedLight,
    onError = Color.White,
    onErrorContainer = Color(0xFF7F1D1D)
)

private val PrepSureDarkColorScheme = darkColorScheme(
    primary = PrepSureBlueVariant,
    onPrimary = Color(0xFF082F49),
    primaryContainer = Color(0xFF0369A1),
    onPrimaryContainer = PrepSureBlueLight,
    secondary = Color(0xFF34D399),
    onSecondary = Color(0xFF022C22),
    secondaryContainer = Color(0xFF065F46),
    onSecondaryContainer = PrepSureGreenLight,
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569)
)

private val PrepSureHighContrastColorScheme = darkColorScheme(
    primary = HighContrastCyan,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF0284C7),
    onPrimaryContainer = Color.White,
    secondary = HighContrastGreen,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF059669),
    onSecondaryContainer = Color.White,
    tertiary = HighContrastYellow,
    onTertiary = Color.Black,
    background = HighContrastBackground,
    onBackground = HighContrastText,
    surface = HighContrastSurface,
    onSurface = HighContrastText,
    surfaceVariant = HighContrastSurfaceCard,
    onSurfaceVariant = HighContrastTextSubtle,
    outline = HighContrastYellow
)

@Composable
fun PrepSureTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    highContrastMode: Boolean = false,
    dynamicColor: Boolean = false, // Set false to prioritize our signature healthcare blue-green branding
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        highContrastMode -> PrepSureHighContrastColorScheme
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> PrepSureDarkColorScheme
        else -> PrepSureLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PrepSureTypography,
        content = content
    )
}
