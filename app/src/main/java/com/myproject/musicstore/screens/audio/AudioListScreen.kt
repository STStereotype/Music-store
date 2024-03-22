package com.myproject.musicstore.screens.audio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import com.myproject.musicstore.domain.models.Audio
import com.myproject.musicstore.screens.audio.models.AudioListEvent
import com.myproject.musicstore.screens.audio.models.AudioListState
import com.myproject.musicstore.screens.audio.widgets.SkeletonAudioList
import com.myproject.musicstore.screens.audio.widgets.AudioList
import com.myproject.musicstore.screens.theme.StyleTheme
import com.myproject.musicstore.ui.theme.MusicMainTheme

@Composable
fun AudioListScreen(
    audioListViewModel: AudioListViewModel,
    isPlaying: Boolean,
    currentAudio: Audio,
    playOrPause: () -> Unit,
    play: (Int) -> Unit,
    onBack: () -> Unit,
) {

    when (val viewState = audioListViewModel.state.collectAsState().value) {
        AudioListState.LoadAudioList -> {
            SkeletonAudioList()
        }

        is AudioListState.SuccessLoadAudioList -> {
            AudioList(
                state = viewState,
                isPlaying = isPlaying,
                currentAudio = currentAudio,
                onBack = onBack,
                playOrPause = playOrPause,
                play = { play(it) }
            )
        }

        AudioListState.ErrorLoadAudioList -> {}
    }

    LaunchedEffect(Unit) { audioListViewModel.send(AudioListEvent.EnterAudioListDisplay) }

    val view = LocalView.current
    val color = MusicMainTheme.colors.darkStubs
    DisposableEffect(Unit) {
        val oldStatusBar = StyleTheme().statusBar(color, view)
        onDispose { StyleTheme().statusBar(Color(oldStatusBar), view) }
    }
}
