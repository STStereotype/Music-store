package com.myproject.musicstore.screens.audio.widgets

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.myproject.musicstore.domain.models.Audio
import com.myproject.musicstore.ui.theme.MusicMainTheme

@Composable
fun AudioListItem(
    idInList: Int,
    audio: Audio,
    isPlaying: Boolean,
    playOrPause: () -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse),
        label = "scale"
    )

    Button(
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        shape = RoundedCornerShape(0.dp),
        onClick = playOrPause
    ) {
        Row(
            modifier = Modifier
                .background(
                    color =
                    if (isPlaying)
                        MusicMainTheme.colors.gray.copy(alpha = 0.1f)
                    else
                        MusicMainTheme.colors.black
                )
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .graphicsLayer {
                        if (isPlaying) {
                            scaleX = scale
                            scaleY = scale
                            transformOrigin = TransformOrigin.Center
                        }
                    }
                    .background(
                        color = if (isPlaying) MusicMainTheme.colors.primary else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .size(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    style = MusicMainTheme.typography.smallText,
                    text = if (isPlaying) "" else "$idInList"
                )
            }
            Image(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(36.dp),
                painter = rememberAsyncImagePainter(model = audio.image),
                contentDescription = null,
            )
            Column(
                modifier = Modifier.height(36.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    style = MusicMainTheme.typography.nameAudio,
                    text = audio.name
                )
                Text(
                    style = MusicMainTheme.typography.nameAuthor,
                    text = audio.author
                )
            }
        }
    }
}
