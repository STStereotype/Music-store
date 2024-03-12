package com.myproject.musicstore.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.myproject.musicstore.R

data class MusicStyleColors(
    val white: Color,
    val black: Color,
    val gray: Color,
    val stubs: Color,
    val purpleLight: Color,
    val purpleDark: Color,
)

data class MusicStyleTypography(
    val title: TextStyle,
    val blockTitle: TextStyle,
    val nameTrack: TextStyle,
    val nameAuthor: TextStyle,
    val smallText: TextStyle
)

data class MusicStyleShape(
    val big: Shape,
    val small: Shape,
)

object MusicMainTheme {
    val colors: MusicStyleColors
        @Composable
        get() = LocalMusicStyleColors.current

    val typography: MusicStyleTypography
        @Composable
        get() = LocalMusicStyleTypography.current

    val shape: MusicStyleShape
        @Composable
        get() = LocalMusicStyleShape.current
}

val Roboto = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
)

val LocalMusicStyleColors = staticCompositionLocalOf<MusicStyleColors> {
    error("No colors provided")
}

val LocalMusicStyleTypography = staticCompositionLocalOf<MusicStyleTypography> {
    error("No font provided")
}

val LocalMusicStyleShape = staticCompositionLocalOf<MusicStyleShape> {
    error("No shapes provided")
}
