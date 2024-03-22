package com.myproject.musicstore.services.media.audio.models.viewModel

sealed class UIAudioState {
    data object Initial : UIAudioState()
    data object Ready : UIAudioState()
}
