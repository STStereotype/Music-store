package com.myproject.musicstore.navigation

sealed class NavGraphTabs(val route: String) {
    data object Main : NavGraphTabs("playlistsNav")
}
