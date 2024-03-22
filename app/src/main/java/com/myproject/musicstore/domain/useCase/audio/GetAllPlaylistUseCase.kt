package com.myproject.musicstore.domain.useCase.audio

import com.myproject.musicstore.domain.models.Playlist
import com.myproject.musicstore.domain.repository.AudioRepository

class GetAllPlaylistUseCase(
    private val mediaRepository: AudioRepository
) {
    suspend fun execute(): List<Playlist> = mediaRepository.getAllPlaylists()
}
