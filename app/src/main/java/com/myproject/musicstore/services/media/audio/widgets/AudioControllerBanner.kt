package com.myproject.musicstore.services.media.audio.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.myproject.musicstore.R
import com.myproject.musicstore.domain.models.Audio
import com.myproject.musicstore.ui.theme.MusicMainTheme

@Composable
fun AudioPlayer(
    audioList: List<Audio>,
    currentPosition: Int,
    isPlaying: Boolean,
    progress: Float,
    playOrPause: () -> Unit,
    onPlay: (Int) -> Unit,
) {
    Box(Modifier.background(MusicMainTheme.colors.black)) {
        AudioPlayer(
            audioList = audioList,
            currentPosition = currentPosition,
            countAudio = audioList.size,
            isPlaying = isPlaying,
            progress = progress,
            onPlay = onPlay,
            playOrPause = playOrPause,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AudioPlayer(
    audioList: List<Audio>,
    currentPosition: Int,
    countAudio: Int,
    isPlaying: Boolean,
    progress: Float,
    onPlay: (Int) -> Unit,
    playOrPause: () -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = currentPosition,
    ) { countAudio }

    HorizontalPager(
        pageSpacing = (-16).dp,
        state = pagerState,
        snapPosition = SnapPosition.End
    ) { position ->
        val audio = audioList[position]
        Column(
            modifier = Modifier
                .height(60.dp) // Hardcore value, z z z
                .padding(horizontal = 12.dp)
                .background(
                    color = Color(0xFF181818),
                    shape = MusicMainTheme.shape.big,
                ),
        ) {
            Slider(
                thumb = {
                    SliderDefaults.Thumb(
                        interactionSource = remember { MutableInteractionSource() },
                        thumbSize = DpSize(0.dp, 0.dp)
                    )
                },
                track = { sliderState ->
                    SliderDefaults.Track(
                        colors = SliderDefaults.colors(
                            activeTrackColor = MusicMainTheme.colors.primary,
                            inactiveTrackColor = MusicMainTheme.colors.stubs
                        ),
                        sliderState = sliderState,
                        thumbTrackGapSize = 0.dp,
                        trackInsideCornerSize = 0.dp,
                        drawStopIndicator = {},
                        modifier = Modifier.height(3.dp),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
                    .height(3.dp),
                value = if (currentPosition == position) progress else 0f,
                onValueChange = {},
                valueRange = 0f..audio.duration.toFloat()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AudioInfo(
                    audioImage = audio.image,
                    audioName = audio.name,
                    authorName = audio.author,
                )
                val icon = if (isPlaying)
                    R.drawable.pause_not_background
                else
                    R.drawable.play_not_background
                Button(
                    modifier = Modifier.size(32.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(16.dp),
                    onClick = playOrPause
                ) {
                    Image(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(id = icon),
                        contentDescription = null
                    )
                }
            }
        }
    }
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != currentPosition)
            onPlay(pagerState.currentPage)
    }
    LaunchedEffect(currentPosition) {
        pagerState.scrollToPage(currentPosition)
    }
}

@Composable
private fun RowScope.AudioInfo(
    audioImage: String,
    audioName: String,
    authorName: String,
) {
    Row(Modifier.weight(1f)) {
        Image(
            modifier = Modifier
                .padding(end = 12.dp)
                .size(36.dp),
            painter = rememberAsyncImagePainter(audioImage),
            contentDescription = null,
        )
        Column(
            modifier = Modifier.height(36.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                maxLines = 1,
                style = MusicMainTheme.typography.nameAudio,
                text = audioName
            )
            Text(
                maxLines = 1,
                style = MusicMainTheme.typography.nameAuthor,
                text = authorName
            )
        }
    }
}
