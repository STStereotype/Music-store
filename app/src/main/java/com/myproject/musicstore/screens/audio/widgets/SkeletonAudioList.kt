package com.myproject.musicstore.screens.audio.widgets

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.myproject.musicstore.screens.components.modifierForStub
import com.myproject.musicstore.ui.theme.MusicMainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkeletonAudioList() {
    val sheetState = rememberStandardBottomSheetState(SheetValue.PartiallyExpanded)
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

    Box {
        BottomSheetScaffold(
            sheetMaxWidth = LocalConfiguration.current.screenWidthDp.dp,
            sheetSwipeEnabled = false,
            sheetDragHandle = {},
            containerColor = MusicMainTheme.colors.darkStubs,
            sheetContainerColor = MusicMainTheme.colors.black,
            sheetPeekHeight = (LocalConfiguration.current.screenHeightDp * 0.4f).dp,
            scaffoldState = scaffoldState,
            sheetContent = {
                LazyColumn {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        )
                    }
                    items(100) { SkeletonAudioListItem() }
                }
            }) {
            contentBottomSheet(LocalConfiguration.current.orientation)
        }
        AudioListHeader(
            playlistName = "",
            visibility = 0f
        ) {}
    }
}

@Composable
private fun contentBottomSheet(
    orientation: Int
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Box(
                modifier = modifierForStub(
                    modifier = Modifier
                        .padding(top = 56.dp)
                        .fillMaxWidth(0.5f)
                        .aspectRatio(1f),
                    cornerRadius = 5.dp
                )
            )
        }
        val padding = if (orientation == Configuration.ORIENTATION_PORTRAIT) 8.dp else 16.dp
        Text(
            modifier = modifierForStub(
                modifier = Modifier
                    .padding(top = padding, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(0.25f),
                cornerRadius = 3.dp
            ),
            text = "",
            style = MusicMainTheme.typography.title,
        )
        Box(
            modifier = modifierForStub(
                modifier = Modifier
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                    .size(64.dp),
                cornerRadius = 32.dp
            )
        )
        Text(
            modifier = modifierForStub(
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    .width(32.dp),
                cornerRadius = 3.dp
            ),
            text = "",
            style = MusicMainTheme.typography.blockTitle,
        )
    }
}
