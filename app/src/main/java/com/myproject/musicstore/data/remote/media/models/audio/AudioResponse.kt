package com.myproject.musicstore.data.remote.media.models.audio

import com.google.gson.annotations.SerializedName

data class AudioResponse(
    @SerializedName("body") val body: Body,
) {
    data class Body(
        @SerializedName("tracks") val audioList: List<AudioItem>,
    )
}
