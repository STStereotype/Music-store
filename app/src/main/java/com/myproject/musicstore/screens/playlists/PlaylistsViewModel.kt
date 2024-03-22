package com.myproject.musicstore.screens.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.musicstore.base.Event
import com.myproject.musicstore.domain.models.Playlist
import com.myproject.musicstore.domain.useCase.audio.GetAllPlaylistUseCase
import com.myproject.musicstore.screens.playlists.models.PlaylistsEvent
import com.myproject.musicstore.screens.playlists.models.PlaylistsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    private val getAllPlaylistUseCase: GetAllPlaylistUseCase
) : ViewModel(), Event<PlaylistsEvent> {

    private val _state: MutableStateFlow<PlaylistsState> =
        MutableStateFlow(PlaylistsState.LoadPlaylists)
    val state: StateFlow<PlaylistsState> = _state

    private val _playlists: MutableStateFlow<List<Playlist>> = MutableStateFlow(listOf())
    val playlists: StateFlow<List<Playlist>> = _playlists

    override fun send(event: PlaylistsEvent) {
        when (val state = _state.value) {
            is PlaylistsState.LoadPlaylists -> reduce(event, state)
            is PlaylistsState.SuccessLoadPlaylists -> reduce(event, state)
            is PlaylistsState.ErrorLoadPlaylists -> reduce(event, state)
        }
    }

    private fun reduce(
        event: PlaylistsEvent,
        state: PlaylistsState.LoadPlaylists
    ) {
        when (event) {
            PlaylistsEvent.EnterPlaylistsDisplay -> fetchPlaylistsDisplay()
        }
    }

    private fun reduce(
        event: PlaylistsEvent,
        state: PlaylistsState.SuccessLoadPlaylists
    ) {
        when (event) {
            PlaylistsEvent.EnterPlaylistsDisplay -> {}
        }
    }

    private fun reduce(
        event: PlaylistsEvent,
        state: PlaylistsState.ErrorLoadPlaylists
    ) {
        when (event) {
            PlaylistsEvent.EnterPlaylistsDisplay -> {}
        }
    }

    private fun fetchPlaylistsDisplay() {
        viewModelScope.launch { if (_state.value == PlaylistsState.LoadPlaylists) getPlaylists() }
    }

    private suspend fun getPlaylists() {
        try {
            _playlists.emit(getAllPlaylistUseCase.execute())
            _state.emit(PlaylistsState.SuccessLoadPlaylists)
        } catch (e: Exception) {
            _playlists.emit(listOf())
            _state.emit(PlaylistsState.ErrorLoadPlaylists)
        }
    }
}
