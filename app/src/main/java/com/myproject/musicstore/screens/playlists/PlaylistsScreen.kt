package com.myproject.musicstore.screens.playlists

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.myproject.musicstore.screens.playlists.models.PlaylistsEvent
import com.myproject.musicstore.screens.playlists.models.PlaylistsState
import com.myproject.musicstore.screens.playlists.widgets.HeaderPlaylists
import com.myproject.musicstore.screens.playlists.widgets.Playlists
import com.myproject.musicstore.screens.playlists.widgets.SkeletonPlaylistScreen

@Composable
fun PlaylistScreen(
    onClick: (playlistId: Int) -> Unit,
    viewModel: PlaylistsViewModel,
) {
    val viewState = viewModel.state.collectAsState().value

    HeaderPlaylists()

    when (viewState) {

        PlaylistsState.LoadPlaylists -> {
            SkeletonPlaylistScreen()
        }

        PlaylistsState.SuccessLoadPlaylists -> {
            Playlists(
                playlists = viewModel.playlists.value,
                onClick = onClick
            )
        }

        PlaylistsState.ErrorLoadPlaylists -> {
            // TODO()
        }
    }

    LaunchedEffect(Unit) { viewModel.send(PlaylistsEvent.EnterPlaylistsDisplay) }
}
