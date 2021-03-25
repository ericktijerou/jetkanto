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

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.theme.PurpleDark
import com.ericktijerou.jetkanto.ui.theme.PurpleLight

@Composable
fun CollapsingScrollTopBar(
    expandedHeight: Dp,
    header: @Composable (scrollProgress: Float) -> Unit,
    scrollContent: @Composable (state: ScrollState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        val scrollState = rememberScrollState()
        val scrollProgress = calculateScrollProgress(expandedHeight, scrollState)

        scrollContent(scrollState)
        header(scrollProgress)
    }
}

@Composable
private fun calculateScrollProgress(height: Dp, state: ScrollState): Float {
    val heightPx = with(LocalDensity.current) { height.toPx() }
    return kotlin.math.max(0f, 1f - state.value / heightPx)
}

@Composable
fun CollapsingTopBarContent(
    title: String,
    scrollProgress: Float,
    expandedHeight: Dp,
    collapsedHeight: Dp,
    onCloseClicked: () -> Unit,
    expandedElevation: Dp = 0.dp,
    collapsedElevation: Dp = 0.dp,
    expandedStartPadding: Dp = 8.dp,
    collapsedStartPadding: Dp = 8.dp + 40.dp,
    titleSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    topBarColor: Color = MaterialTheme.colors.primary,
    topBarTintExpandedColor: Color = MaterialTheme.colors.onBackground,
    topBarTintCollapsedColor: Color = MaterialTheme.colors.onPrimary,
    body: @Composable () -> Unit
) {
    val height = calculateHeight(scrollProgress, expandedHeight, collapsedHeight)
    val elevation = calculateElevation(scrollProgress, expandedElevation, collapsedElevation)
    val topBarCalculatedColor = calculateTopBarColor(scrollProgress, topBarColor)
    val topBarTintColor =
        calculateTopBarTintColor(scrollProgress, topBarTintExpandedColor, topBarTintCollapsedColor)
    val borderRadius = calculateRadius(scrollProgress, 28.dp, 0.dp)
    Card(
        elevation = elevation,
        shape = RectangleShape,
        backgroundColor = KantoTheme.colors.background,
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(expandedHeight)
                .background(
                    brush = Brush.verticalGradient(listOf(PurpleDark, PurpleLight)),
                    shape = RoundedCornerShape(bottomStart = borderRadius, bottomEnd = borderRadius)
                )
        )
        body()
        Box(
            contentAlignment = Alignment.CenterStart,
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
            )
        }
        Surface(
            color = topBarCalculatedColor,
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(bottomStart = borderRadius, bottomEnd = borderRadius)
        ) {}
        Box(
            contentAlignment = Alignment.CenterStart,
        ) {
            if (scrollProgress == 0f) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .height(collapsedHeight)
                        .align(Alignment.BottomStart),
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h4.copy(
                            fontSize = titleSize,
                            color = topBarTintColor
                        ),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
            Box(
                Modifier
                    .height(collapsedHeight)
                    .align(Alignment.TopEnd)
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { onCloseClicked() }
                ) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Close",
                        tint = topBarTintColor
                    )
                }
            }
        }
    }
}

private fun calculateHeight(scrollProgress: Float, expandedHeight: Dp, collapsedHeight: Dp): Dp {
    val calculatedHeight = expandedHeight * scrollProgress
    return max(calculatedHeight, collapsedHeight)
}

private fun calculateElevation(
    scrollProgress: Float,
    expandedElevation: Dp,
    collapsedElevation: Dp
): Dp {
    val calculatedElevation = expandedElevation * (1f - scrollProgress)
    return max(calculatedElevation, collapsedElevation)
}

private fun calculateStartPadding(
    scrollProgress: Float,
    expandedStartPadding: Dp,
    collapsedStartPadding: Dp
): Dp {
    val difference = expandedStartPadding - collapsedStartPadding
    return collapsedStartPadding + difference * scrollProgress
}

private fun calculateRadius(
    scrollProgress: Float,
    expandedRadius: Dp,
    collapsedRadius: Dp
): Dp {
    val difference = expandedRadius - collapsedRadius
    return collapsedRadius + difference * scrollProgress
}

private fun calculateTitleSizeScale(
    scrollProgress: Float,
    expandedTitleSize: TextUnit,
    collapsedTitleSize: TextUnit
): Float {
    val difference = collapsedTitleSize.value / expandedTitleSize.value
    return difference + ((1f - difference) * scrollProgress)
}

private fun calculateTopBarColor(scrollProgress: Float, color: Color): Color {
    return color.copy(alpha = 1f - scrollProgress)
}

@Composable
private fun calculateTopBarTintColor(
    scrollProgress: Float,
    expandedColor: Color,
    collapsedColor: Color
): Color {
    return colorOnBooleanAnimation(expandedColor, collapsedColor, scrollProgress < 0.5f)
}

@Composable
fun colorOnBooleanAnimation(
    initialValue: Color,
    targetValue: Color,
    isTargetValue: Boolean,
): Color {
    val targetColor = if (isTargetValue) targetValue else initialValue
    val animatedColor = remember { Animatable(targetColor) }
    LaunchedEffect(targetColor, tween<Float>()) {
        animatedColor.animateTo(targetColor)
    }
    return animatedColor.value
}

fun Modifier.scrim(colors: List<Color>): Modifier = drawWithContent {
    drawContent()
    drawRect(Brush.verticalGradient(colors))
}

@Stable
fun Modifier.scale(
    scale: Float,
    pivotX: Float = centerX,
    pivotY: Float = centerY,
) = graphicsLayer(
    scaleX = scale,
    scaleY = scale,
    transformOrigin = TransformOrigin(pivotX, pivotY)
)

private const val centerX = 0.5f
private const val centerY = 0.5f
