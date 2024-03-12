package com.myproject.musicstore.data.remote.media.models.track

import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName("body") val body: Body,
) {
    data class Body(
        @SerializedName("tracks") val tracks: List<TrackItem>,
    )
}
