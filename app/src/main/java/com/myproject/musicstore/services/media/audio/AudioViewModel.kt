package com.myproject.musicstore.services.media.audio

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.musicstore.domain.models.Audio
import com.myproject.musicstore.services.media.audio.models.handler.AudioState
import com.myproject.musicstore.services.media.audio.models.handler.PlayerEvents
import com.myproject.musicstore.services.media.audio.models.viewModel.UIAudioEvents
import com.myproject.musicstore.services.media.audio.models.viewModel.UIAudioState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import javax.inject.Inject

private val audioDummy = Audio(
    url = "",
    id = 0,
    author = "",
    name = "",
    duration = 0,
    image = "",
)

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val audioServiceHandler: AudioServiceHandler,
) : ViewModel() {
    var progress = mutableStateOf(0f)
    var isPlaying = mutableStateOf(false)
    var currentAudio = mutableStateOf(audioDummy)
    var currentPlaylistId = mutableStateOf(-1)
    var currentAudioId = mutableStateOf(-1)
    var audioList = mutableStateOf(listOf<Audio>())

    private val _uiAudioState: MutableStateFlow<UIAudioState> =
        MutableStateFlow(UIAudioState.Initial)

    // Parameter initialization block
    init {
        viewModelScope.launch {
            currentAudioId.value = audioServiceHandler.currentAudioPosition()
            isPlaying.value = audioServiceHandler.isPlaying()
            val isAudioStopped = !isPlaying.value && audioServiceHandler.isDone()
            if (isAudioStopped) pause()
            calculateProgressValue(audioServiceHandler.currentProgress())
        }
    }

    // State initialization block
    init {
        viewModelScope.launch {
            audioServiceHandler.audioState.collectLatest { mediaState ->
                when (mediaState) {
                    AudioState.Initial -> _uiAudioState.value = UIAudioState.Initial
                    AudioState.Ready -> _uiAudioState.value = UIAudioState.Ready
                    is AudioState.Playing -> isPlaying.value = mediaState.isPlaying
                    is AudioState.Progress -> calculateProgressValue(mediaState.progress)
                    is AudioState.CurrentPlaying ->
                        if (audioList.value.isNotEmpty())
                            currentAudio.value = audioList.value[mediaState.mediaItemIndex]
                }
            }
        }
    }

    fun mediaControllerPrepared() = isPlaying.value || audioServiceHandler.isDone()

    fun send(uiAudioEvents: UIAudioEvents) {
        viewModelScope.launch {
            when (uiAudioEvents) {
                is UIAudioEvents.Play -> play(uiAudioEvents.index)
                UIAudioEvents.Pause -> pause()

                is UIAudioEvents.UpdateMediaItems -> setAudioList(uiAudioEvents.items)
            }
        }
    }

    private fun setAudioList(audioLists: List<Audio>) {
        viewModelScope.launch {
            audioList.value = audioLists
            setMediaItems()
        }
    }

    private suspend fun setMediaItems() =
        audioServiceHandler.send(PlayerEvents.UpdateMediaItems(audioList.value))

    private fun calculateProgressValue(currentProgress: Long) {
        progress.value =
            if (currentProgress > 0) currentProgress / 1000f
            else 0f
    }

    private suspend fun play(index: Int) {
        currentAudio.value = audioList.value[index]
        audioServiceHandler.send(PlayerEvents.Play(index))
    }

    private suspend fun pause() = audioServiceHandler.send(PlayerEvents.Pause)

    override fun onCleared() {
        runBlocking { launch { audioServiceHandler.send(PlayerEvents.StopProgress) } }
        super.onCleared()
    }
}
