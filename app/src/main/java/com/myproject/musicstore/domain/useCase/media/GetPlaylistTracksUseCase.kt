package com.myproject.musicstore.domain.useCase.media

import com.myproject.musicstore.domain.models.Track
import com.myproject.musicstore.domain.repository.MediaRepository

class GetPlaylistTracksUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend fun execute(idPlaylist: Int): List<Track> =
        mediaRepository.gelPlaylistTracks(idPlaylist)
}
