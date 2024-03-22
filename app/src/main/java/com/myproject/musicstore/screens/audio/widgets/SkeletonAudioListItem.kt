package com.myproject.musicstore.screens.audio.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myproject.musicstore.screens.components.modifierForStub
import com.myproject.musicstore.ui.theme.MusicMainTheme

@Composable
fun SkeletonAudioListItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifierForStub(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .width(15.dp),
                cornerRadius = 2.dp
            ),
            style = MusicMainTheme.typography.smallText,
            text = ""
        )
        Box(
            modifier = modifierForStub(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(36.dp),
                cornerRadius = 5.dp
            )
        )
        Column(
            modifier = Modifier.height(36.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = modifierForStub(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    cornerRadius = 3.dp
                ),
                style = MusicMainTheme.typography.nameAudio,
                text = ""
            )
            Text(
                modifier = modifierForStub(
                    modifier = Modifier.fillMaxWidth(0.25f),
                    cornerRadius = 3.dp
                ),
                style = MusicMainTheme.typography.nameAuthor,
                text = ""
            )
        }
    }
}
