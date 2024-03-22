package com.myproject.musicstore

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myproject.musicstore.domain.models.Audio
import com.myproject.musicstore.navigation.NavGraphTabs
import com.myproject.musicstore.navigation.mainGraph
import com.myproject.musicstore.services.media.audio.AudioViewModel
import com.myproject.musicstore.services.media.audio.models.viewModel.UIAudioEvents
import com.myproject.musicstore.services.media.audio.widgets.AudioPlayer
import com.myproject.musicstore.ui.theme.MusicMainTheme
import com.myproject.musicstore.ui.theme.MusicStoreTheme
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var sharedPrefAudio: SharedPrefAudio
    private val audioViewModel: AudioViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        sharedPrefAudio = SharedPrefAudio(getSharedPreferences("audioViewModel", MODE_PRIVATE))
        if (audioViewModel.mediaControllerPrepared()) {
            loadSharedPref()
        }

        setContent {
            MusicStoreTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MusicMainTheme.colors.black,
                ) {
                    Scaffold(
                        containerColor = MusicMainTheme.colors.black,
                        bottomBar = {
                            if (audioViewModel.mediaControllerPrepared()) {
                                val isPlaying = audioViewModel.isPlaying.value
                                val position = audioViewModel.currentAudio.value.id
                                val audioList = audioViewModel.audioList.value
                                fun pause() = audioViewModel.send(UIAudioEvents.Pause)
                                fun play(position: Int) =
                                    audioViewModel.send(UIAudioEvents.Play(position))
                                AudioPlayer(
                                    audioList = audioList,
                                    currentPosition = position,
                                    isPlaying = isPlaying,
                                    progress = audioViewModel.progress.value,
                                    playOrPause = {
                                        if (isPlaying) pause()
                                        else play(position)
                                    },
                                    onPlay = { play(it) },
                                )
                            }
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = NavGraphTabs.Main.route,
                            enterTransition = { EnterTransition.None },
                            exitTransition = { ExitTransition.None },
                        ) {
                            mainGraph(
                                navController = navController,
                                audioViewModel = audioViewModel,
                                playOrPause = { playlistId, audioId, audioList ->
                                    val currentPlaylistId = audioViewModel.currentPlaylistId.value
                                    val currentAudio = audioViewModel.currentAudio.value
                                    var position = currentAudio.id
                                    val isPlaylist = playlistId == currentPlaylistId
                                    val isPlaying = audioViewModel.isPlaying.value

                                    when (audioId) {
                                        null -> {
                                            if (isPlaying && isPlaylist)
                                                audioViewModel.send(UIAudioEvents.Pause)
                                            else if (!isPlaying && isPlaylist)
                                                audioViewModel.send(UIAudioEvents.Play(position))
                                            else
                                                position = 0
                                        }

                                        else -> {
                                            if (audioId == position && isPlaying && isPlaylist)
                                                audioViewModel.send(UIAudioEvents.Pause)
                                            else if (isPlaylist)
                                                audioViewModel.send(UIAudioEvents.Play(audioId))
                                            else
                                                position = audioId
                                        }
                                    }
                                    if (!isPlaylist) {
                                        audioViewModel.send(UIAudioEvents.UpdateMediaItems(audioList))
                                        audioViewModel.currentAudio.value = audioList[position]
                                        audioViewModel.currentPlaylistId.value = playlistId
                                        audioViewModel.send(UIAudioEvents.Play(position))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadSharedPref() {
        if (audioViewModel.audioList.value.isEmpty()) {
            val date = sharedPrefAudio.loadDate()
            val audioList = date.audioList

            audioViewModel.currentPlaylistId.value = date.currentPlaylistId

            if (audioList.isEmpty()) return

            val currentAudioId = audioViewModel.currentAudioId.value
            audioViewModel.audioList.value = audioList
            audioViewModel.currentAudio.value =
                if (currentAudioId <= -1)
                    audioList[0]
                else
                    audioList[currentAudioId]
        }
    }

    override fun onPause() {
        super.onPause()
        sharedPrefAudio.saveDate(
            SharedPrefAudio.SharedPrefDate(
                audioViewModel.currentPlaylistId.value,
                audioViewModel.audioList.value,
            )
        )
    }
}

private class SharedPrefAudio(
    private val sharedPref: SharedPreferences
) {
    private val audioListType: Type = object : TypeToken<List<Audio>>() {}.type
    private val gson = Gson()

    companion object {
        private const val CURRENT_PLAYLIST_ID = "CURRENT_PLAYLIST_ID"
        private const val AUDIO_LIST = "AUDIO_LIST"
    }

    fun loadDate(): SharedPrefDate {
        val audioList =
            gson.fromJson<List<Audio>>(sharedPref.getString(AUDIO_LIST, ""), audioListType)
        return SharedPrefDate(
            currentPlaylistId = sharedPref.getInt(CURRENT_PLAYLIST_ID, -1),
            audioList = audioList,
        )
    }

    fun saveDate(sharedPrefDate: SharedPrefDate) {
        sharedPref.edit().apply {
            val gson = Gson()
            putInt(CURRENT_PLAYLIST_ID, sharedPrefDate.currentPlaylistId)
            putString(AUDIO_LIST, gson.toJson(sharedPrefDate.audioList))
        }.apply()
    }

    data class SharedPrefDate(
        val currentPlaylistId: Int,
        val audioList: List<Audio>,
    )
}
