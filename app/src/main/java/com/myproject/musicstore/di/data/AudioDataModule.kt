package com.myproject.musicstore.di.data

import com.myproject.musicstore.data.remote.media.AudioApi
import com.myproject.musicstore.data.repository.media.AudioRepositoryImpl
import com.myproject.musicstore.domain.repository.AudioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AudioDataModule {
    @Provides
    @Singleton
    fun provideMediaApi(retrofit: Retrofit): AudioApi =
        retrofit.create(AudioApi::class.java)

    @Provides
    @Singleton
    fun provideMediaRepository(audioApi: AudioApi): AudioRepository =
        AudioRepositoryImpl(audioApi)
}
