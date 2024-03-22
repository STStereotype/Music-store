package com.myproject.musicstore.navigation

sealed class MainGraphDestination(val destination: String) {
    data object Playlists :
        MainGraphDestination("${NavGraphTabs.Main.route}_playlists")

    data object PlaylistAudioList :
        MainGraphDestination("${NavGraphTabs.Main.route}_playlists/{playlist_id}")
}
