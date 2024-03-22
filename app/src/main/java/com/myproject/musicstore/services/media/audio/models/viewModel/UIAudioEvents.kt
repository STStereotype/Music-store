package com.myproject.musicstore.services.media.audio.models.viewModel

import com.myproject.musicstore.domain.models.Audio

sealed class UIAudioEvents {
    data object Pause : UIAudioEvents()
    data class Play(val index: Int) : UIAudioEvents()
    data class UpdateMediaItems(val items: List<Audio>) : UIAudioEvents()
}
