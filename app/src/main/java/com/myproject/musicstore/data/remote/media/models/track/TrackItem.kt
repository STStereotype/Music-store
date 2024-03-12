package com.myproject.musicstore.data.remote.media.models.track

import com.google.gson.annotations.SerializedName

data class TrackItem(
    @SerializedName("url") val url: String,
    @SerializedName("position") val position: Int,
    @SerializedName("author") val author: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
)
