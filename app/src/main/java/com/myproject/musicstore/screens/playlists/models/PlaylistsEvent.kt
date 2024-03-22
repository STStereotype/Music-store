package com.myproject.musicstore.screens.playlists.models

sealed class PlaylistsEvent {
    data object EnterPlaylistsDisplay : PlaylistsEvent()
}
