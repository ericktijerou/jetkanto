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
package com.ericktijerou.jetkanto.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.ericktijerou.jetkanto.ui.component.player.LocalVideoPlayerController
import com.ericktijerou.jetkanto.ui.theme.KantoTheme

@Composable
fun KantoProgressIndicator(
    modifier: Modifier = Modifier
) {
    val controller = LocalVideoPlayerController.current
    val videoPlayerUiState by controller.collect()
    with(videoPlayerUiState) {
        if (currentPosition >= duration) return@with
        val progress = currentPosition.coerceAtMost(duration).toFloat() / duration.toFloat()
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
        ProgressIndicator(
            progress = animatedProgress,
            modifier = modifier,
            strokeWidth = 2.dp,
            color = KantoTheme.customColors.textPrimaryColor
        )
    }
}

@Composable
fun ProgressIndicator(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity),
    strokeWidth: Dp = 4.dp
) {
    Canvas(
        modifier
            .progressSemantics(progress)
            .size(LinearIndicatorWidth, LinearIndicatorHeight)
            .focusable()
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        drawLinearIndicatorBackground(backgroundColor, strokeWidthPx)
        drawLinearIndicator(0f, progress, color, strokeWidthPx)
    }
}

private fun DrawScope.drawLinearIndicatorBackground(
    color: Color,
    strokeWidth: Float
) = drawLinearIndicator(0f, 1f, color, strokeWidth)

private fun DrawScope.drawLinearIndicator(
    startFraction: Float,
    endFraction: Float,
    color: Color,
    strokeWidth: Float
) {
    val width = size.width
    val height = size.height
    val yOffset = height / 2
    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = (if (isLtr) startFraction else 1f - endFraction) * width
    val barEnd = (if (isLtr) endFraction else 1f - startFraction) * width
    drawLine(color, Offset(barStart, yOffset), Offset(barEnd, yOffset), strokeWidth)
}

private val LinearIndicatorHeight = ProgressIndicatorDefaults.StrokeWidth
private val LinearIndicatorWidth = 240.dp
