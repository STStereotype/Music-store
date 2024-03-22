package com.myproject.musicstore.di.domain

import com.myproject.musicstore.domain.repository.AudioRepository
import com.myproject.musicstore.domain.useCase.audio.GetAllPlaylistUseCase
import com.myproject.musicstore.domain.useCase.audio.GetAudioListsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AudioDomainModule {
    @Provides
    @Singleton
    fun provideGetAllPlaylistUseCase(audioRepository: AudioRepository): GetAllPlaylistUseCase =
        GetAllPlaylistUseCase(audioRepository)

    @Provides
    @Singleton
    fun provideGetAudioListUseCase(audioRepository: AudioRepository): GetAudioListsUseCase =
        GetAudioListsUseCase(audioRepository)
}
