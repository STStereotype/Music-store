package com.myproject.musicstore.navigation

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.myproject.musicstore.di.ViewModelFactoryProvider
import com.myproject.musicstore.domain.models.Audio
import com.myproject.musicstore.screens.playlists.PlaylistScreen
import com.myproject.musicstore.screens.playlists.PlaylistsViewModel
import com.myproject.musicstore.screens.audio.AudioListScreen
import com.myproject.musicstore.screens.audio.AudioListViewModel
import com.myproject.musicstore.screens.audio.models.AudioListState
import com.myproject.musicstore.services.media.audio.AudioViewModel
import dagger.hilt.android.EntryPointAccessors

fun NavGraphBuilder.mainGraph(
    navController: NavController,
    audioViewModel: AudioViewModel,
    playOrPause: (playlistId: Int, audioId: Int?, audioList: List<Audio>) -> Unit,
) {
    navigation(
        route = NavGraphTabs.Main.route,
        startDestination = MainGraphDestination.Playlists.destination
    ) {
        composable(MainGraphDestination.Playlists.destination) {
            val viewModel: PlaylistsViewModel = hiltViewModel()
            Column {
                PlaylistScreen(
                    onClick = { id ->
                        val destinations = MainGraphDestination.PlaylistAudioList.destination
                            .substringBefore("{")
                        navController.navigate("$destinations$id")
                    },
                    viewModel = viewModel
                )
            }
        }
        composable(
            route = MainGraphDestination.PlaylistAudioList.destination,
            arguments = listOf(navArgument("playlist_id") {
                type = NavType.IntType
            }),
        ) { backStackEntry ->
            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                ViewModelFactoryProvider::class.java
            ).audioListViewModelProvider()

            val playlistId = backStackEntry.arguments?.getInt("playlist_id")
            val viewModel: AudioListViewModel = viewModel(
                factory = AudioListViewModel.provideAudioListViewModelFactory(
                    factory,
                    playlistId
                )
            )
            val currentAudio = audioViewModel.currentAudio.value
            val isPlaylist = playlistId == audioViewModel.currentPlaylistId.value
            val isPlaying = audioViewModel.isPlaying.value

            Column {
                AudioListScreen(
                    audioListViewModel = viewModel,
                    isPlaying = isPlaying && isPlaylist,
                    currentAudio = currentAudio,
                    playOrPause = {
                        val audioList =
                            (viewModel.state.value as AudioListState.SuccessLoadAudioList)
                                .audioList // duplication, preferably removed
                        playOrPause(playlistId!!, null, audioList)
                    },
                    play = {
                        val audioList =
                            (viewModel.state.value as AudioListState.SuccessLoadAudioList)
                                .audioList // duplication, preferably removed
                        playOrPause(playlistId!!, it, audioList)
                    }
                ) {
                    navController.navigate(
                        route = MainGraphDestination.Playlists.destination,
                        navOptions = navOptions {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    )
                }
            }
        }
    }
}
