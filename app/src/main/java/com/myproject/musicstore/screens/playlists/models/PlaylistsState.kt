package com.myproject.musicstore.screens.playlists.models

sealed class PlaylistsState {
    data object LoadPlaylists : PlaylistsState()
    data object SuccessLoadPlaylists : PlaylistsState()
    data object ErrorLoadPlaylists : PlaylistsState()
}
