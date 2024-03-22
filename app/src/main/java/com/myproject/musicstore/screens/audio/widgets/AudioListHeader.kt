package com.myproject.musicstore.screens.audio.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myproject.musicstore.R
import com.myproject.musicstore.ui.theme.MusicMainTheme

@Composable
fun AudioListHeader(
    playlistName: String,
    visibility: Float,
    onBack: () -> Unit
) {
    val headerAlpha = if (visibility >= 0.97f) 0.75f else 0f
    Box(
        modifier = Modifier
            .background(MusicMainTheme.colors.black.copy(alpha = headerAlpha))
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onBack,
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                tint = MusicMainTheme.colors.white,
                painter = painterResource(id = R.drawable.back),
                contentDescription = null
            )
        }
        if (visibility >= 0.8) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = playlistName,
                    style = MusicMainTheme.typography.blockTitle
                )
            }
        }
    }
}
