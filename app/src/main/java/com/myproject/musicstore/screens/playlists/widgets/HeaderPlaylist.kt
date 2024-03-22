package com.myproject.musicstore.screens.playlists.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myproject.musicstore.ui.theme.MusicMainTheme

@Composable
fun HeaderPlaylists() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MusicMainTheme.colors.black.copy(alpha = (0.75f)))
    ) {
        Text(
            text = "Плейлисты",
            style = MusicMainTheme.typography.title,
        )
    }
}
