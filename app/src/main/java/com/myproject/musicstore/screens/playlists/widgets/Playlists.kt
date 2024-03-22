package com.myproject.musicstore.screens.playlists.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myproject.musicstore.domain.models.Playlist

@Composable
fun Playlists(
    playlists: List<Playlist>,
    onClick: (id: Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
        columns = GridCells.Fixed(2),
    ) {
        items(playlists.size) { index ->
            PlaylistsItem(
                playlists = playlists[index],
                onClick = onClick
            )
        }
        item { Box(modifier = Modifier.height(60.dp)) } // Hardcore value, z z z
    }
}
