package com.myproject.musicstore.data.remote.media

import com.myproject.musicstore.data.remote.media.models.playlist.PlaylistResponse
import com.myproject.musicstore.data.remote.media.models.audio.AudioResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AudioApi {
    @GET("/v2/playlists/")
    suspend fun getPlaylist(): PlaylistResponse

    @GET("/v2/playlists/{playlist_id}/")
    suspend fun getAudioList(
        @Path("playlist_id") playlistId: Int
    ): AudioResponse
}
