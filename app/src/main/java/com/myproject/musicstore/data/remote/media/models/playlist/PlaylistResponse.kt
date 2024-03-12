package com.myproject.musicstore.data.remote.media.models.playlist

import com.google.gson.annotations.SerializedName

data class PlaylistResponse(
    @SerializedName("body") val body: Body,
) {
    data class Body(
        @SerializedName("playlists") val playlists: List<PlaylistItem>
    )
}
