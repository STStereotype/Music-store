package com.myproject.musicstore.domain.models

data class Audio(
    val url: String,
    val id: Int,
    val author: String,
    val name: String,
    val duration: Long,
    val image: String,
)
