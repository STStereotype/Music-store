package com.myproject.musicstore.domain.models

data class Playlist(
    val id: Int,
    val name: String,
    val description: String,
    val image: String?,
)
