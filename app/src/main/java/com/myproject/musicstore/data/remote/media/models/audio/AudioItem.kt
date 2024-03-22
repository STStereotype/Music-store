package com.myproject.musicstore.data.remote.media.models.audio

import com.google.gson.annotations.SerializedName

data class AudioItem(
    @SerializedName("url") val url: String,
    @SerializedName("position") val position: Int,
    @SerializedName("author") val author: String,
    @SerializedName("name") val name: String,
    @SerializedName("duration") val duration: Long,
    @SerializedName("image") val image: String,
)
