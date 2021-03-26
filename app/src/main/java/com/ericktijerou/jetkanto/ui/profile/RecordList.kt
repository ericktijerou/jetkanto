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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.ericktijerou.jetkanto.R
import com.ericktijerou.jetkanto.core.headerExpandedHeight
import com.ericktijerou.jetkanto.ui.component.player.DefaultVideoPlayerController
import com.ericktijerou.jetkanto.ui.component.player.KantoPlayer
import com.ericktijerou.jetkanto.ui.component.player.VideoPlayerState
import com.ericktijerou.jetkanto.ui.entity.RecordView
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.theme.Teal500
import dev.chrisbanes.accompanist.coil.CoilImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecordList(modifier: Modifier = Modifier, list: List<RecordView>, scrollState: LazyListState) {
    LazyColumn(state = scrollState, modifier = modifier) {
        stickyHeader {
            Box(Modifier.padding(top = headerExpandedHeight))
        }
        itemsIndexed(
            items = list,
            itemContent = { index, record ->
                RecordCard(record = record, false)
            }
        )
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RecordCard(record: RecordView, focused: Boolean) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = KantoTheme.customColors.videoCardColor,
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
            PlayerWithControls(
                record = record,
                focused = focused,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
            )
            RecordFooter(
                likes = record.likeCount.toString(),
                modifier = Modifier.padding(start = 8.dp),
                onLikeClick = {},
                liked = true
            )
        }
    }
}

@Composable
fun PlayerWithControls(record: RecordView, modifier: Modifier = Modifier, focused: Boolean) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val videoPlayerController = DefaultVideoPlayerController(
        context = context,
        initialState = VideoPlayerState(),
        coroutineScope = coroutineScope
    ).apply {
        setVideoUrl(record.videoUrl)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(videoPlayerController, lifecycleOwner) {
        val observer = object : DefaultLifecycleObserver {
            override fun onPause(owner: LifecycleOwner) {
                videoPlayerController.pause()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(modifier = modifier) {
        KantoPlayer(
            videoPlayerController = videoPlayerController,
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxWidth(),
            controlsEnabled = true
        )
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
fun RecordFooter(likes: String, modifier: Modifier, onLikeClick: () -> Unit, liked: Boolean) {
    IconButton(onClick = onLikeClick, modifier = modifier) {
        val color = if (liked) Teal500 else KantoTheme.customColors.textPrimaryColor
        val icon = if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = color
            )
            Text(
                text = likes,
                color = color,
                style = KantoTheme.typography.body1,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}
