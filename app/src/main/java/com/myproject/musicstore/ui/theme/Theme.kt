package com.myproject.musicstore.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun MusicStoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) BaseLightPalette else BaseLightPalette
    val typography = Typography
    val shape = Shapes

    CompositionLocalProvider {
        LocalMusicStyleColors provides colors
        LocalMusicStyleTypography provides typography
        LocalMusicStyleShape provides shape
    }
}
