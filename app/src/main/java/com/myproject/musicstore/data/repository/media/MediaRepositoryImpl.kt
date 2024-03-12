package com.myproject.musicstore.data.repository.media

import com.myproject.musicstore.data.remote.media.MediaApi
import com.myproject.musicstore.data.remote.media.models.playlist.PlaylistItem
import com.myproject.musicstore.data.remote.media.models.track.TrackItem
import com.myproject.musicstore.domain.models.Playlist
import com.myproject.musicstore.domain.models.Track
import com.myproject.musicstore.domain.repository.MediaRepository

class MediaRepositoryImpl(
    private val mediaApi: MediaApi,
) : MediaRepository {
    override suspend fun getAllPlaylists(): List<Playlist> =
        mediaApi.getPlaylist().body.playlists.mapToListPlaylists()

    override suspend fun gelPlaylistTracks(idPlaylist: Int): List<Track> =
        mediaApi.getPlaylistTracks(idPlaylist).body.tracks.mapToListTracks()

    private fun List<PlaylistItem>.mapToListPlaylists(): List<Playlist> =
        this.map {
            Playlist(
                id = it.id,
                name = it.name,
                description = it.description,
                image = it.image,
            )
        }

    private fun List<TrackItem>.mapToListTracks(): List<Track> =
        this.map {
            Track(
                url = it.url,
                position = it.position,
                author = it.author,
                name = it.name,
                image = it.image,
            )
        }
}
