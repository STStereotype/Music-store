package com.myproject.musicstore.services.media.audio.models.handler

import com.myproject.musicstore.domain.models.Audio

sealed class PlayerEvents {
    data object Pause : PlayerEvents()
    data object StopProgress : PlayerEvents()
    data class Play(val index: Int) : PlayerEvents()
    data class UpdateMediaItems(val items: List<Audio>) : PlayerEvents()
}
