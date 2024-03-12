package com.myproject.musicstore.domain.useCase.media

import com.myproject.musicstore.domain.models.Playlist
import com.myproject.musicstore.domain.repository.MediaRepository

class GetAllPlaylistUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend fun execute(): List<Playlist> = mediaRepository.getAllPlaylists()
}
