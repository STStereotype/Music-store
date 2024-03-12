package com.myproject.musicstore.data.remote.media.models.playlist

import com.google.gson.annotations.SerializedName

data class PlaylistItem(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String?,
)
