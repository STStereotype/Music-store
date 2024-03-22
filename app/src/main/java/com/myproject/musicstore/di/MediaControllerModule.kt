package com.myproject.musicstore.di

import android.content.ComponentName
import android.content.Context
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.myproject.musicstore.services.media.audio.AudioServiceHandler
import com.myproject.musicstore.services.media.audio.PlaybackService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MediaControllerModule {
    @Provides
    @Singleton
    fun provideSessionToken(@ApplicationContext context: Context): SessionToken =
        SessionToken(context, ComponentName(context, PlaybackService::class.java))

    @Provides
    @Singleton
    fun provideControllerFuture(@ApplicationContext context: Context, sessionToken: SessionToken):
            ListenableFuture<MediaController> =
        MediaController.Builder(context, sessionToken).buildAsync()

    @Provides
    @Singleton
    fun provideServiceHandler(
        controllerFuture: ListenableFuture<MediaController>
    ): AudioServiceHandler = AudioServiceHandler(controllerFuture)
}
