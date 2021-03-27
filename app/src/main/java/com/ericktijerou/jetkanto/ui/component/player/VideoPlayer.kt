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
package com.ericktijerou.jetkanto.ui.component.player

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.ericktijerou.jetkanto.ui.component.KantoProgressIndicator
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView

internal val LocalVideoPlayerController =
    compositionLocalOf<DefaultVideoPlayerController> { error("VideoPlayerController is not initialized") }

@Composable
fun rememberVideoPlayerController(
    videoUrl: String? = null
): VideoPlayerController {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    return rememberSaveable(
        context, coroutineScope,
        saver = object : Saver<DefaultVideoPlayerController, VideoPlayerState> {
            override fun restore(value: VideoPlayerState): DefaultVideoPlayerController {
                return DefaultVideoPlayerController(
                    context = context,
                    initialState = value,
                    coroutineScope = coroutineScope
                )
            }

            override fun SaverScope.save(value: DefaultVideoPlayerController): VideoPlayerState {
                return value.currentState { it }
            }
        },
        init = {
            DefaultVideoPlayerController(
                context = context,
                initialState = VideoPlayerState(),
                coroutineScope = coroutineScope
            ).apply {
                videoUrl?.let { setVideoUrl(it) }
            }
        }
    )
}

@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun VideoPlayer(
    videoPlayerController: VideoPlayerController,
    modifier: Modifier = Modifier,
    controlsEnabled: Boolean = true,
    backgroundColor: Color = Color.Black
) {
    require(videoPlayerController is DefaultVideoPlayerController) {
        "Use [rememberVideoPlayerController] to create an instance of [VideoPlayerController]"
    }
    SideEffect {
        videoPlayerController.videoPlayerBackgroundColor = backgroundColor.value.toInt()
        videoPlayerController.enableControls(controlsEnabled)
    }
    CompositionLocalProvider(
        LocalContentColor provides KantoTheme.customColors.videoCardColor,
        LocalVideoPlayerController provides videoPlayerController
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            KantoProgressIndicator(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(0.5.dp))
            Box(
                modifier = Modifier
                    .background(color = backgroundColor)
                    .fillMaxSize()
                    .then(modifier)
            ) {
                PlayerSurface {
                    videoPlayerController.playerViewAvailable(it)
                }
                MediaControlButtons(
                    modifier = Modifier.matchParentSize()
                )
            }
        }
    }
}

@Composable
fun PlayerSurface(
    modifier: Modifier = Modifier,
    onPlayerViewAvailable: (PlayerView) -> Unit = {}
) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                onPlayerViewAvailable(this)
            }
        },
        modifier = modifier
    )
}
