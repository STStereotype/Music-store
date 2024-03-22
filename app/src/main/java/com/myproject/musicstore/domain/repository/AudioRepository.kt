package com.myproject.musicstore.domain.repository

import com.myproject.musicstore.domain.models.Playlist
import com.myproject.musicstore.domain.models.Audio

interface AudioRepository {
    suspend fun getAllPlaylists(): List<Playlist>
    suspend fun gelAudioList(playlistId: Int): List<Audio>
}
