package com.myproject.musicstore.services.media.audio

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import com.myproject.musicstore.domain.models.Audio
import com.myproject.musicstore.services.media.audio.models.handler.AudioState
import com.myproject.musicstore.services.media.audio.models.handler.PlayerEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioServiceHandler @Inject constructor(
    private val controllerFuture: ListenableFuture<MediaController>,
) : Player.Listener {
    private var scope: CoroutineScope? = null
    private val _audioState: MutableStateFlow<AudioState> =
        MutableStateFlow(AudioState.Initial)
    val audioState: StateFlow<AudioState> = _audioState.asStateFlow()

    suspend fun send(
        playerEvent: PlayerEvents,
    ) {
        when (playerEvent) {
            PlayerEvents.Pause -> pause()
            PlayerEvents.StopProgress -> stopProgressUpdate()
            is PlayerEvents.Play -> play(playerEvent.index)
            is PlayerEvents.UpdateMediaItems -> setMediaItemList(playerEvent.items)
        }
    }

    fun isPlaying() = if (isDone()) controllerFuture.get().isPlaying else false

    fun currentAudioPosition() = if (isDone()) controllerFuture.get().currentMediaItemIndex else 0

    fun currentProgress() = if (isDone()) controllerFuture.get().currentPosition else 0

    fun isDone() = controllerFuture.isDone

    override fun onTracksChanged(tracks: Tracks) {
        _audioState.value = AudioState.CurrentPlaying(controllerFuture.get().currentMediaItemIndex)
        super.onTracksChanged(tracks)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _audioState.value = AudioState.Playing(isPlaying)
        super.onIsPlayingChanged(isPlaying)
    }

    private suspend fun controller() = withContext(Dispatchers.IO) { controllerFuture.get() }

    private suspend fun setMediaItemList(mediaItems: List<Audio>) {
        mediaItems.map { audio ->
            MediaItem.Builder()
                .setMediaId("media-1")
                .setUri(audio.url)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setAlbumArtist(audio.author)
                        .setDisplayTitle(audio.name)
                        .setSubtitle(audio.name)
                        .build()
                )
                .build()
        }.also {
            controller().setMediaItems(it)
            controller().prepare()
            _audioState.value = AudioState.Ready
            controller().removeListener(this)
            controller().addListener(this)
        }
    }

    private suspend fun progressUpdate() {
        stopProgressUpdate()
        scope = CoroutineScope(Job() + Dispatchers.Main)
        scope!!.launch {
            while (true) {
                delay(500)
                _audioState.value = AudioState.Progress(controller().currentPosition)
            }
        }
    }

    private fun stopProgressUpdate() = scope?.cancel()

    private suspend fun play(selectedAudioIndex: Int) {
        _audioState.value = AudioState.Playing(true)
        if (controller().currentMediaItemIndex != selectedAudioIndex)
            controller().seekToDefaultPosition(selectedAudioIndex)
        controller().play()
        progressUpdate()
    }

    private suspend fun pause() {
        _audioState.value = AudioState.Playing(false)
        controller().pause()
        stopProgressUpdate()
    }
}
