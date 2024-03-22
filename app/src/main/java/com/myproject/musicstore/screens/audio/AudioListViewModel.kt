package com.myproject.musicstore.screens.audio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.myproject.musicstore.base.Event
import com.myproject.musicstore.domain.useCase.audio.GetAllPlaylistUseCase
import com.myproject.musicstore.domain.useCase.audio.GetAudioListsUseCase
import com.myproject.musicstore.screens.audio.models.AudioListEvent
import com.myproject.musicstore.screens.audio.models.AudioListState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AudioListViewModel @AssistedInject constructor(
    private val getAudioListUseCase: GetAudioListsUseCase,
    private val getAllPlaylistUseCase: GetAllPlaylistUseCase,
    @Assisted private val playlistId: Int?,
) : ViewModel(), Event<AudioListEvent> {

    private val _state: MutableStateFlow<AudioListState> =
        MutableStateFlow(AudioListState.LoadAudioList)
    val state: StateFlow<AudioListState> = _state

    @AssistedFactory
    interface Factory {
        fun create(playlistId: Int?): AudioListViewModel
    }

    companion object {
        fun provideAudioListViewModelFactory(
            factory: Factory,
            playlistId: Int?,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                factory.create(playlistId) as T
        }
    }

    override fun send(event: AudioListEvent) {
        when (val state = _state.value) {
            is AudioListState.LoadAudioList -> reduce(event, state)
            is AudioListState.SuccessLoadAudioList -> reduce(event, state)
            is AudioListState.ErrorLoadAudioList -> reduce(event, state)
        }
    }

    private fun reduce(
        event: AudioListEvent,
        state: AudioListState.LoadAudioList,
    ) {
        when (event) {
            AudioListEvent.EnterAudioListDisplay -> fetchAudioListDisplay()
        }
    }

    private fun reduce(
        event: AudioListEvent,
        state: AudioListState.SuccessLoadAudioList,
    ) {
        when (event) {
            AudioListEvent.EnterAudioListDisplay -> {}
        }
    }

    private fun reduce(
        event: AudioListEvent,
        state: AudioListState.ErrorLoadAudioList,
    ) {
        when (event) {
            AudioListEvent.EnterAudioListDisplay -> {}
        }
    }

    private fun fetchAudioListDisplay() {
        viewModelScope.launch {
            if (_state.value == AudioListState.LoadAudioList) loadAudioList()
        }
    }

    private suspend fun loadAudioList() {
        try {
            val playlist = getAllPlaylistUseCase.execute().first { playlist ->
                playlist.id == playlistId
            }
            val audio = getAudioListUseCase.execute(playlistId!!)
            _state.emit(
                AudioListState.SuccessLoadAudioList(
                    audioList = audio,
                    playlistImage = playlist.image,
                    playlistName = playlist.name
                )
            )
        } catch (e: Exception) {
            _state.emit(AudioListState.ErrorLoadAudioList)
        }
    }
}
