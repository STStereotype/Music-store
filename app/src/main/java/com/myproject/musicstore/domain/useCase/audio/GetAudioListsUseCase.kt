package com.myproject.musicstore.domain.useCase.audio

import com.myproject.musicstore.domain.models.Audio
import com.myproject.musicstore.domain.repository.AudioRepository

class GetAudioListsUseCase(
    private val mediaRepository: AudioRepository
) {
    suspend fun execute(playlistId: Int): List<Audio> =
        mediaRepository.gelAudioList(playlistId)
}
