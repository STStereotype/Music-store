package com.myproject.musicstore.screens.playlists.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myproject.musicstore.screens.components.modifierForStub
import com.myproject.musicstore.ui.theme.MusicMainTheme

@Composable
fun PlaylistSkeletonItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 24.dp),
    ) {
        Box(
            modifier = modifierForStub(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                cornerRadius = 5.dp
            )
        )
        Text(
            modifier = modifierForStub(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(0.75f),
                cornerRadius = 5.dp
            ),
            text = "",
            style = MusicMainTheme.typography.blockTitle,
        )
    }
}
