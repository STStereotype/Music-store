package com.myproject.musicstore.screens.playlists.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SkeletonPlaylistScreen() {
    LazyVerticalGrid(
        userScrollEnabled = false,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
        columns = GridCells.Fixed(2),
    ) {
        items(10) {
            PlaylistSkeletonItem()
        }
    }
}
