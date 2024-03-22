package com.myproject.musicstore.screens.audio.models

import com.myproject.musicstore.domain.models.Audio

sealed class AudioListState {
    data object LoadAudioList : AudioListState()
    data class SuccessLoadAudioList(
        val audioList: List<Audio>,
        val playlistImage: String?,
        val playlistName: String,
    ) : AudioListState()

    data object ErrorLoadAudioList : AudioListState()
}
