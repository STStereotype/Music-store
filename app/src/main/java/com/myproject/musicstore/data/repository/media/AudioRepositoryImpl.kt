package com.myproject.musicstore.data.repository.media

import com.myproject.musicstore.data.remote.media.AudioApi
import com.myproject.musicstore.data.remote.media.models.playlist.PlaylistItem
import com.myproject.musicstore.data.remote.media.models.audio.AudioItem
import com.myproject.musicstore.domain.models.Playlist
import com.myproject.musicstore.domain.models.Audio
import com.myproject.musicstore.domain.repository.AudioRepository

class AudioRepositoryImpl(
    private val audioApi: AudioApi,
) : AudioRepository {
    override suspend fun getAllPlaylists(): List<Playlist> =
        audioApi.getPlaylist().body.playlists.mapToListPlaylists()

    override suspend fun gelAudioList(playlistId: Int): List<Audio> =
        audioApi.getAudioList(playlistId).body.audioList.mapToListAudioList()

    private fun List<PlaylistItem>.mapToListPlaylists(): List<Playlist> =
        this.map {
            Playlist(
                id = it.id,
                name = it.name,
                description = it.description,
                image = it.image,
            )
        }

    private fun List<AudioItem>.mapToListAudioList(): List<Audio> =
        this.map {
            Audio(
                url = it.url,
                id = it.position - 1,
                author = it.author,
                name = it.name,
                duration = it.duration,
                image = it.image,
            )
        }
}
