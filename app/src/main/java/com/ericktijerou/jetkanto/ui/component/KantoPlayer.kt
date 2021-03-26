package com.ericktijerou.jetkanto.ui.component

import android.content.Context
import android.media.MediaCodec
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.ericktijerou.jetkanto.App
import com.ericktijerou.jetkanto.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.util.Util

@Composable
fun KantoPlayer(context: Context, url: String, selected: Boolean) {
    val kantoPlayer = remember {
        SimpleExoPlayer.Builder(context)
            .build()
            .apply {
                App.simpleCache?.let { cache ->
                    val defaultHttpDataSourceFactory = DefaultHttpDataSourceFactory(
                        Util.getUserAgent(
                            context,
                            context.getString(R.string.app_name)
                        )
                    )
                    val mediaDataSourceFactory = CacheDataSource.Factory().setCache(cache)
                        .setUpstreamDataSourceFactory(defaultHttpDataSourceFactory)
                    val mediaSource =
                        ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(
                            MediaItem.fromUri(url)
                        )
                    setMediaSource(mediaSource, false)
                    prepare()
                }

            }
    }
    kantoPlayer.videoScalingMode = MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT
    kantoPlayer.repeatMode = Player.REPEAT_MODE_ONE

    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = {
            PlayerView(it).apply {
                useController = false
                player = kantoPlayer
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            }
        }
    )

    kantoPlayer.playWhenReady = selected

    DisposableEffect(key1 = url) {
        onDispose {
            kantoPlayer.release()
        }
    }
}