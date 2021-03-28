/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.jetkanto.ui.profile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.ericktijerou.jetkanto.R
import com.ericktijerou.jetkanto.core.headerExpandedHeight
import com.ericktijerou.jetkanto.ui.component.player.VideoPlayer
import com.ericktijerou.jetkanto.ui.component.player.rememberVideoPlayerController
import com.ericktijerou.jetkanto.ui.entity.RecordView
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.theme.Teal500
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecordList(
    modifier: Modifier = Modifier,
    list: List<RecordView>,
    scrollState: LazyListState,
    autoPlay: Boolean,
    onFavoriteClick: (Long, Boolean) -> Unit
) {
    val playerJob = remember { mutableStateOf<Job?>(null) }
    LazyColumn(state = scrollState, modifier = modifier) {
        stickyHeader {
            Box(Modifier.padding(top = headerExpandedHeight))
        }
        itemsIndexed(
            items = list,
            itemContent = { index, record ->
                val focused = autoPlay && index == scrollState.firstVisibleItemIndex
                RecordCard(
                    record = record,
                    focused = focused,
                    onFavoriteClick = onFavoriteClick,
                    playerJob = playerJob
                )
            }
        )
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RecordCard(
    record: RecordView,
    focused: Boolean,
    onFavoriteClick: (Long, Boolean) -> Unit,
    playerJob: MutableState<Job?>
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = KantoTheme.customColors.cardColor,
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 0.dp
    ) {
        Column {
            RecordHeader(
                record = record,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
            KantoPlayer(
                record = record,
                focused = focused,
                playerJob = playerJob,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
            )
            RecordFooter(
                record = record,
                modifier = Modifier.padding(start = 8.dp),
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

@Composable
fun KantoPlayer(
    record: RecordView,
    modifier: Modifier = Modifier,
    focused: Boolean,
    playerJob: MutableState<Job?>
) {
    val videoPlayerController = rememberVideoPlayerController()
    val showPreview = remember { mutableStateOf(true) }
    if (focused) {
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(videoPlayerController, lifecycleOwner) {
            val observer = object : DefaultLifecycleObserver {
                override fun onPause(owner: LifecycleOwner) {
                    videoPlayerController.pause()
                }

                override fun onResume(owner: LifecycleOwner) {
                    videoPlayerController.play()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }
    Box(modifier = modifier) {
        if (!focused) {
            showPreview.value = true
            videoPlayerController.pause()
        } else {
            VideoPlayer(
                videoPlayerController = videoPlayerController,
                backgroundColor = KantoTheme.customColors.cardColor,
                modifier = Modifier.fillMaxWidth(),
                controlsEnabled = false
            )
            LaunchedEffect(key1 = videoPlayerController) {
                playerJob.value?.cancel()
                delay(750)
                showPreview.value = false
                playerJob.value = launch {
                    videoPlayerController.setVideoUrl(record.videoUrl)
                    videoPlayerController.play()
                }
            }
        }
        if (showPreview.value) {
            VideoPreview(modifier = Modifier.fillMaxSize(), previewUrl = record.preview)
        }
        Icon(
            imageVector = Icons.Outlined.Videocam,
            contentDescription = stringResource(R.string.label_video_icon),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        )
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = stringResource(R.string.label_video_icon),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp)
        )
        Text(
            text = record.songName,
            color = Color.White,
            style = KantoTheme.typography.body1.copy(fontWeight = FontWeight.Black),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        )
    }
}

@Composable
fun VideoPreview(modifier: Modifier, previewUrl: String) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CoilImage(
            data = previewUrl,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(
                R.string.label_video_preview
            )
        )
        Canvas(modifier = Modifier.size(64.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawCircle(
                color = Color.Black.copy(alpha = 0.2f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 2
            )
        }
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = stringResource(
                R.string.label_video_preview
            ),
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun RecordHeader(record: RecordView, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoilImage(
            data = record.ownerAvatar,
            contentDescription = stringResource(R.string.label_avatar),
            fadeIn = true,
            modifier = Modifier
                .clip(CircleShape)
                .border(5.dp, Color.White.copy(alpha = 0.2f), CircleShape)
                .size(36.dp)
        )
        Text(
            text = record.ownerName,
            modifier = Modifier.padding(start = 12.dp),
            color = KantoTheme.customColors.textPrimaryColor,
            style = KantoTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = stringResource(R.string.label_sang),
            modifier = Modifier.padding(start = 4.dp),
            color = KantoTheme.customColors.textPrimaryColor,
            style = KantoTheme.typography.body1
        )
        Text(
            text = record.songName,
            modifier = Modifier.padding(start = 4.dp),
            color = KantoTheme.customColors.textPrimaryColor,
            style = KantoTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun RecordFooter(
    record: RecordView,
    modifier: Modifier,
    onFavoriteClick: (Long, Boolean) -> Unit
) {
    IconButton(onClick = { onFavoriteClick(record.id, record.isFavorite) }, modifier = modifier) {
        val color = if (record.isFavorite) Teal500 else KantoTheme.customColors.textPrimaryColor
        val icon = if (record.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = color
            )
            Text(
                text = record.likeCount.toString(),
                color = color,
                style = KantoTheme.typography.body1,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}
