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

import android.content.Context
import android.media.MediaCodec
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.ericktijerou.jetkanto.App
import com.ericktijerou.jetkanto.R
import com.ericktijerou.jetkanto.core.set
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.video.VideoListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class DefaultVideoPlayerController(
    private val context: Context,
    private val initialState: VideoPlayerState,
    private val coroutineScope: CoroutineScope
) : VideoPlayerController {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<VideoPlayerState>
        get() = _state.asStateFlow()

    private var initialStateRunner: (() -> Unit)? = {
        exoPlayer.seekTo(initialState.currentPosition)
    }

    fun <T> currentState(filter: (VideoPlayerState) -> T): T {
        return filter(_state.value)
    }

    @Composable
    fun collect(): State<VideoPlayerState> {
        return _state.collectAsState()
    }

    @Composable
    fun <T> collect(filter: VideoPlayerState.() -> T): State<T> {
        return remember(filter) {
            _state.map { it.filter() }
        }.collectAsState(
            initial = _state.value.filter()
        )
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    var videoPlayerBackgroundColor: Int = DefaultVideoPlayerBackgroundColor.value.toInt()
        set(value) {
            field = value
            playerView?.setBackgroundColor(value)
        }

    private lateinit var videoUrl: String
    private var playerView: PlayerView? = null

    private var updateDurationAndPositionJob: Job? = null
    private val playerEventListener = object : Player.EventListener {

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            if (PlaybackState.of(playbackState) == PlaybackState.READY) {
                initialStateRunner = initialStateRunner?.let {
                    it.invoke()
                    null
                }

                updateDurationAndPositionJob?.cancel()
                updateDurationAndPositionJob = coroutineScope.launch {
                    while (this.isActive) {
                        updateDurationAndPosition()
                        delay(250)
                    }
                }
            }

            _state.set {
                copy(
                    isPlaying = playWhenReady,
                    playbackState = PlaybackState.of(playbackState)
                )
            }
        }
    }

    private val videoListener = object : VideoListener {
        override fun onVideoSizeChanged(
            width: Int,
            height: Int,
            unappliedRotationDegrees: Int,
            pixelWidthHeightRatio: Float
        ) {
            super.onVideoSizeChanged(width, height, unappliedRotationDegrees, pixelWidthHeightRatio)

            _state.set {
                copy(videoSize = width.toFloat() to height.toFloat())
            }
        }
    }

    /**
     * Internal exoPlayer instance
     */
    private val exoPlayer = SimpleExoPlayer.Builder(context)
        .build()
        .apply {
            playWhenReady = initialState.isPlaying
            addListener(playerEventListener)
            addVideoListener(videoListener)
            videoScalingMode = MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT
        }

    init {
        exoPlayer.playWhenReady = initialState.isPlaying
    }

    private val waitPlayerViewToPrepare = AtomicBoolean(false)

    override fun play() {
        if (exoPlayer.playbackState == Player.STATE_ENDED) {
            exoPlayer.seekTo(0)
        }
        exoPlayer.playWhenReady = true
    }

    override fun pause() {
        exoPlayer.playWhenReady = false
    }

    override fun playPauseToggle() {
        if (exoPlayer.isPlaying) pause()
        else play()
    }

    override fun setVideoUrl(url: String) {
        this.videoUrl = url
        if (playerView == null) {
            waitPlayerViewToPrepare.set(true)
        } else {
            prepare()
        }
    }

    fun enableControls(enabled: Boolean) {
        _state.set { copy(controlsEnabled = enabled) }
    }

    fun showControls() {
        _state.set { copy(controlsVisible = true) }
    }

    fun hideControls() {
        _state.set { copy(controlsVisible = false) }
    }

    private fun updateDurationAndPosition() {
        _state.set {
            copy(
                duration = exoPlayer.duration.coerceAtLeast(0),
                currentPosition = exoPlayer.currentPosition.coerceAtLeast(0)
            )
        }
    }

    private fun prepare() {
        fun createVideoSource(): MediaSource? {
            return App.simpleCache?.let { cache ->
                val defaultHttpDataSourceFactory = DefaultHttpDataSourceFactory(
                    Util.getUserAgent(
                        context,
                        context.getString(R.string.app_name)
                    )
                )
                val mediaDataSourceFactory = CacheDataSource.Factory().setCache(cache)
                    .setUpstreamDataSourceFactory(defaultHttpDataSourceFactory)
                ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(
                    MediaItem.fromUri(videoUrl)
                )
            }
        }
        createVideoSource()?.let {
            exoPlayer.setMediaSource(it, false)
            exoPlayer.prepare()
        }
    }

    fun playerViewAvailable(playerView: PlayerView) {
        this.playerView = playerView
        playerView.player = exoPlayer
        playerView.setBackgroundColor(videoPlayerBackgroundColor)

        if (waitPlayerViewToPrepare.compareAndSet(true, false)) {
            prepare()
        }
    }

    override fun reset() {
        exoPlayer.stop()
    }
}

val DefaultVideoPlayerBackgroundColor = Color.Black
