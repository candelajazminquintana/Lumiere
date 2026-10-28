package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SalonColorScheme = lightColorScheme(
    primary = MarsalaPrimary,
    onPrimary = Color.White,
    primaryContainer = RoseSoft,
    onPrimaryContainer = MarsalaDark,
    secondary = RoseAccent,
    onSecondary = Color.White,
    secondaryContainer = RosePill,
    onSecondaryContainer = MarsalaPrimary,
    tertiary = GoldAccent,
    onTertiary = Color.White,
    background = CreamBackground,
    onBackground = TextDark,
    surface = CreamSurface,
    onSurface = TextDark,
    surfaceVariant = RosePill,
    onSurfaceVariant = TextMuted,
    outline = RoseSoft
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SalonColorScheme,
        typography = Typography,
        content = content
    )
}
