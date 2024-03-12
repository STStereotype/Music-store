package com.myproject.musicstore.di.domain

import com.myproject.musicstore.domain.repository.MediaRepository
import com.myproject.musicstore.domain.useCase.media.GetAllPlaylistUseCase
import com.myproject.musicstore.domain.useCase.media.GetPlaylistTracksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MediaDomainModule {
    @Provides
    @Singleton
    fun provideGetAllPlaylistUseCase(mediaRepository: MediaRepository): GetAllPlaylistUseCase =
        GetAllPlaylistUseCase(mediaRepository)

    @Provides
    @Singleton
    fun provideGetPlaylistTracksUseCase(mediaRepository: MediaRepository): GetPlaylistTracksUseCase =
        GetPlaylistTracksUseCase(mediaRepository)
}
