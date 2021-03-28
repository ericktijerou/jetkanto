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
package com.ericktijerou.jetkanto.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.ericktijerou.jetkanto.ui.util.LocalSysUiController

private val DarkColorPalette = darkColors(
    primary = BlackLight,
    primaryVariant = BlackLight,
    background = BackgroundDark,
    surface = BlackLight,
)

private val LightColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = Color.White,
    background = BackgroundLight,
    surface = Color.White,
)

private val LightPuppyColorPalette = KantoColors(
    textPrimaryColor = Color.Black,
    textSecondaryColor = TextSecondaryLight,
    cardColor = Color.White,
    isDark = false
)

private val DarkPuppyColorPalette = KantoColors(
    textPrimaryColor = Color.White,
    textSecondaryColor = TextSecondaryDark,
    cardColor = GrayCardDark,
    isDark = true
)

@Composable
fun KantoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val (colors, customColors) = if (darkTheme) DarkColorPalette to DarkPuppyColorPalette else LightColorPalette to LightPuppyColorPalette
    val sysUiController = LocalSysUiController.current
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = colors.primary
        )
    }
    ProvideKantoColors(customColors) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = Shapes,
            content = content
        )
    }
}

object KantoTheme {
    val customColors: KantoColors
        @Composable
        @ReadOnlyComposable
        get() = LocalKantoColors.current

    val colors: Colors
        @Composable
        get() = MaterialTheme.colors

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography
}

@Stable
class KantoColors(
    textPrimaryColor: Color,
    textSecondaryColor: Color,
    cardColor: Color,
    isDark: Boolean
) {
    var textPrimaryColor by mutableStateOf(textPrimaryColor)
        internal set
    var textSecondaryColor by mutableStateOf(textSecondaryColor)
        internal set
    var cardColor by mutableStateOf(cardColor)
        internal set
    var isDark by mutableStateOf(isDark)
        internal set

    fun copy(
        textPrimaryColor: Color = this.textPrimaryColor,
        textSecondaryColor: Color = this.textSecondaryColor,
        cardColor: Color = this.cardColor,
        isDark: Boolean = this.isDark
    ): KantoColors = KantoColors(
        textPrimaryColor,
        textSecondaryColor,
        cardColor,
        isDark
    )
}

internal fun KantoColors.update(other: KantoColors) {
    textPrimaryColor = other.textPrimaryColor
    textSecondaryColor = other.textSecondaryColor
    cardColor = other.cardColor
    isDark = other.isDark
}

@Composable
fun ProvideKantoColors(
    colors: KantoColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors.copy() }.apply {
        update(colors)
    }
    CompositionLocalProvider(LocalKantoColors provides colorPalette, content = content)
}

private val LocalKantoColors = staticCompositionLocalOf<KantoColors> {
    error("No KantoColorPalette provided")
}
