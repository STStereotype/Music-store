package com.myproject.musicstore.screens.audio.models

sealed class AudioListEvent {
    data object EnterAudioListDisplay : AudioListEvent()
}
