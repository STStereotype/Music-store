package com.myproject.musicstore.services.media.audio.models.handler

sealed class AudioState {
    data object Initial : AudioState()
    data object Ready : AudioState()
    data class Playing(val isPlaying: Boolean) : AudioState()
    data class Progress(val progress: Long) : AudioState()
    data class CurrentPlaying(val mediaItemIndex: Int) : AudioState()
}
