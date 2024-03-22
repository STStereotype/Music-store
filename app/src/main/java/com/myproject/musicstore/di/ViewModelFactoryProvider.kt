package com.myproject.musicstore.di

import com.myproject.musicstore.screens.audio.AudioListViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun audioListViewModelProvider(): AudioListViewModel.Factory
}
