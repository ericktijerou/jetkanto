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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth,
    image: @Composable (imageSize: Dp) -> Unit,
) {
    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val stroke = with(LocalDensity.current) {
            Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
        }
        CircumferenceAvatar(
            stroke = stroke,
            color = color.copy(alpha = 0.1f),
            modifier = Modifier.size(maxWidth)
        )
        CircumferenceAvatar(
            stroke = stroke,
            color = color.copy(alpha = 0.3f),
            modifier = Modifier.size(maxWidth - 20.dp)
        )
        CircleAvatar(modifier = Modifier.size(maxWidth - 40.dp), color = color.copy(alpha = 0.4f))
        image(maxWidth - 40.dp)
    }
}


@Composable
fun CircleAvatar(modifier: Modifier, color: Color) {
    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawCircle(
            color = color,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            radius = size.minDimension / 2
        )
    }
}

@Composable
fun CircumferenceAvatar(stroke: Stroke, modifier: Modifier, color: Color) {
    Canvas(modifier = modifier) {
        val startAngle = 270f
        val diameterOffset = stroke.width / 2
        drawArcBackground(
            startAngle,
            360f,
            color,
            stroke,
            diameterOffset
        )
    }
}

fun DrawScope.drawArcBackground(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke,
    diameterOffset: Float
) {
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}
