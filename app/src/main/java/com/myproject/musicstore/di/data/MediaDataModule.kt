package com.myproject.musicstore.di.data

import com.myproject.musicstore.data.remote.media.MediaApi
import com.myproject.musicstore.data.repository.media.MediaRepositoryImpl
import com.myproject.musicstore.domain.repository.MediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MediaDataModule {
    @Provides
    @Singleton
    fun provideMediaApi(retrofit: Retrofit): MediaApi =
        retrofit.create(MediaApi::class.java)

    @Provides
    @Singleton
    fun provideMediaRepository(mediaApi: MediaApi): MediaRepository =
        MediaRepositoryImpl(mediaApi)
}
