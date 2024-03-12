package com.myproject.musicstore.domain.models

data class Track(
    val url: String,
    val position: Int,
    val author: String,
    val name: String,
    val image: String,
)
