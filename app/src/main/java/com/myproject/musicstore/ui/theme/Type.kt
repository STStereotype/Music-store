package com.myproject.musicstore.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// FontWeight
private val regular = FontWeight(400)
private val medium = FontWeight(500)

// TextStyle
private val defaultValue = TextStyle(
    fontFamily = Roboto,
    fontSize = 14.sp,
    fontWeight = regular,
    color = BaseLightPalette.white,
)

val Typography = MusicStyleTypography(
    title = defaultValue.copy(
        fontSize = 18.sp,
        fontWeight = medium,
    ),
    blockTitle = defaultValue.copy(fontSize = 14.sp),
    nameTrack = defaultValue.copy(
        fontSize = 14.sp,
        fontWeight = medium,
    ),
    nameAuthor = defaultValue.copy(
        fontSize = 12.sp,
        color = BaseLightPalette.gray,
    ),
    smallText = defaultValue.copy(
        fontSize = 10.sp,
        color = BaseLightPalette.gray,
    ),
)
