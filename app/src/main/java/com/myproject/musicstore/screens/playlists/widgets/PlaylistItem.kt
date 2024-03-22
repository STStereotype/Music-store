package com.myproject.musicstore.screens.playlists.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.myproject.musicstore.R
import com.myproject.musicstore.domain.models.Playlist
import com.myproject.musicstore.ui.theme.MusicMainTheme

@Composable
fun PlaylistsItem(
    playlists: Playlist,
    onClick: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 24.dp)
            .clickable { onClick.invoke(playlists.id) },
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            shape = MusicMainTheme.shape.big,
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            val image =
                if (playlists.image != null)
                    rememberAsyncImagePainter(model = playlists.image)
                else
                    painterResource(id = R.drawable.stubs)
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            text = playlists.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MusicMainTheme.typography.blockTitle,
        )
    }
}
