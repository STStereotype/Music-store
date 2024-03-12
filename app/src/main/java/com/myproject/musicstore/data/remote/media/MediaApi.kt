package com.myproject.musicstore.data.remote.media

import com.myproject.musicstore.data.remote.media.models.playlist.PlaylistResponse
import com.myproject.musicstore.data.remote.media.models.track.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MediaApi {
    @GET("/v2/playlists/")
    suspend fun getPlaylist(): PlaylistResponse

    @GET("/v2/playlists/{playlist_id}/")
    suspend fun getPlaylistTracks(
        @Path("playlist_id") playlistId: Int
    ): TrackResponse
}
