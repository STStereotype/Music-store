package com.myproject.musicstore.screens.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.unit.Dp
import com.myproject.musicstore.ui.theme.MusicMainTheme

@Composable
fun modifierForStub(modifier: Modifier, cornerRadius: Dp): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = MusicMainTheme.colors.initialValueColor,
        targetValue = MusicMainTheme.colors.targetValue,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "color",
    )
    return modifier
        .drawBehind {
            drawRoundRect(
                color = animatedColor,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }
}
