package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = FF_Orange,                // Fire Orange
    onPrimary = Color.White,
    primaryContainer = FF_Orange_Light,
    onPrimaryContainer = Color.White,
    secondary = FF_Yellow,               // Gold accent
    onSecondary = Color.Black,
    tertiary = NeonBlue,
    onTertiary = Color.Black,
    background = FF_Dark_Bg,             // Core slate black
    onBackground = FF_Text_Primary,
    surface = FF_Card_Bg,               // Dark elevated card
    onSurface = FF_Text_Primary,
    surfaceVariant = FF_Card_Bg_Tinted,
    onSurfaceVariant = FF_Text_Secondary,
    outline = FF_Orange,
    error = FF_Red,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force dark theme for gaming vibe!
    dynamicColor: Boolean = false, // Disable dynamic colors to keep Free Fire branding
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
