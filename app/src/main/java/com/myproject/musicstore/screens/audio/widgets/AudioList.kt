package com.myproject.musicstore.screens.audio.widgets

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.myproject.musicstore.R
import com.myproject.musicstore.domain.models.Audio
import com.myproject.musicstore.screens.audio.models.AudioListState
import com.myproject.musicstore.screens.theme.StyleTheme
import com.myproject.musicstore.ui.theme.MusicMainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioList(
    state: AudioListState.SuccessLoadAudioList,
    isPlaying: Boolean,
    currentAudio: Audio,
    onBack: () -> Unit,
    playOrPause: () -> Unit,
    play: (Int) -> Unit,
) {
    val sheetState = rememberStandardBottomSheetState(SheetValue.PartiallyExpanded)
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

    val maxHeight = remember { mutableStateOf(-1f) }
    val visibility = remember {
        derivedStateOf {
            try {
                val scrollOffset = scaffoldState.bottomSheetState.requireOffset()
                if (maxHeight.value == -1f)
                    maxHeight.value = scrollOffset
                val result = (maxHeight.value - scrollOffset) / maxHeight.value
                if (result >= 0.99f || result.isNaN()) 1f
                else if (result <= 0.01) 0f
                else result
            } catch (_: Exception) {
                0f
            }
        }
    }
    val view = LocalView.current
    val color = MusicMainTheme.colors.darkStubs.copy(alpha = 1f - visibility.value)
    StyleTheme().statusBar(color, view)

    Box {
        BottomSheetScaffold(
            sheetMaxWidth = LocalConfiguration.current.screenWidthDp.dp + 2.dp,
            containerColor = MusicMainTheme.colors.darkStubs,
            sheetContainerColor = MusicMainTheme.colors.black,
            sheetPeekHeight = (LocalConfiguration.current.screenHeightDp * 0.4f).dp,
            scaffoldState = scaffoldState,
            sheetDragHandle = {},
            sheetContent = {
                AudioListItems(
                    audioList = state.audioList,
                    isPlaying = isPlaying,
                    playOrPause = play,
                    currentAudio = currentAudio,
                )
            }) {
            contentBottomSheet(
                playlistImage = state.playlistImage,
                playlistName = state.playlistName,
                isPlaying = isPlaying,
                playOrPause = playOrPause,
                visibility = visibility.value < 0.8,
                orientation = LocalConfiguration.current.orientation
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MusicMainTheme.colors.black.copy(alpha = visibility.value))
            )
        }
        AudioListHeader(
            playlistName = state.playlistName,
            visibility = visibility.value,
            onBack = onBack,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AudioListItems(
    audioList: List<Audio>,
    isPlaying: Boolean,
    currentAudio: Audio,
    playOrPause: (audioId: Int) -> Unit,
) {
    LazyColumn {
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
        items(audioList.size) { index ->
            val audio = audioList[index]
            AudioListItem(
                idInList = index,
                audio = audio,
                isPlaying = isPlaying && currentAudio.id == audio.id,
            ) { playOrPause(audio.id) }
        }
        item { Box(modifier = Modifier.height(60.dp)) } // Hardcore value, z z z
    }
}

@Composable
private fun contentBottomSheet(
    playlistImage: String?,
    playlistName: String,
    isPlaying: Boolean,
    visibility: Boolean,
    orientation: Int,
    playOrPause: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Card(
                modifier = Modifier
                    .padding(top = 56.dp)
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f),
                shape = MusicMainTheme.shape.big,
                colors = CardDefaults.cardColors(containerColor = MusicMainTheme.colors.stubs),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            ) {
                val image =
                    if (playlistImage != null) rememberAsyncImagePainter(model = playlistImage)
                    else painterResource(id = R.drawable.stubs)
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    painter = image,
                    contentDescription = null,
                )
            }
        }
        val padding = if (orientation == Configuration.ORIENTATION_PORTRAIT) 8.dp else 16.dp
        if (visibility) {
            Text(
                modifier = Modifier.padding(top = padding, start = 16.dp, end = 16.dp),
                text = playlistName,
                style = MusicMainTheme.typography.title,
            )
        }
        Button(
            modifier = Modifier.padding(top = 20.dp, start = 16.dp, end = 16.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = { playOrPause() }
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                painter = painterResource(if (isPlaying) R.drawable.pause else R.drawable.play),
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
            text = "Play",
            style = MusicMainTheme.typography.blockTitle,
        )
    }
}
