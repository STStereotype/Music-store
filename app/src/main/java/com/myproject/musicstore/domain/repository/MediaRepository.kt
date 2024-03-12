package com.myproject.musicstore.domain.repository

import com.myproject.musicstore.domain.models.Playlist
import com.myproject.musicstore.domain.models.Track

interface MediaRepository {
    suspend fun getAllPlaylists(): List<Playlist>
    suspend fun gelPlaylistTracks(idPlaylist: Int): List<Track>
}
