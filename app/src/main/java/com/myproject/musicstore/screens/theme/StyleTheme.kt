package com.myproject.musicstore.screens.theme

import android.app.Activity
import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat

class StyleTheme {
    fun statusBar(color: Color, view: View): Int {
        val window = (view.context as Activity).window
        val oldColor = window.statusBarColor
        window.statusBarColor = color.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        return oldColor
    }
}
