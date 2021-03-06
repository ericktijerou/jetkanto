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

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ericktijerou.jetkanto.core.headerCollapsedHeight
import com.ericktijerou.jetkanto.core.headerExpandedHeight
import com.ericktijerou.jetkanto.ui.component.CollapsingScrollTopBar
import com.ericktijerou.jetkanto.ui.component.CollapsingTopBarHeader
import com.ericktijerou.jetkanto.ui.component.Loader
import com.ericktijerou.jetkanto.ui.entity.orEmpty
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.util.ThemedPreview
import com.ericktijerou.jetkanto.ui.util.hiltViewModel

@Composable
fun ProfileScreen(modifier: Modifier, goToEditProfile: () -> Unit, toggleTheme: () -> Unit) {
    val viewModel: ProfileViewModel by hiltViewModel()
    CollapsingScrollTopBar(
        expandedHeight = headerExpandedHeight,
        header = { scrollProgress, scrollY ->
            val session = viewModel.session.collectAsState(initial = null).value.orEmpty()
            CollapsingTopBarHeader(
                title = session.name,
                scrollProgress = scrollProgress,
                expandedHeight = headerExpandedHeight,
                collapsedHeight = headerCollapsedHeight,
                topBarTintCollapsedColor = KantoTheme.customColors.textPrimaryColor,
                topBarTintExpandedColor = Color.White,
                toggleTheme = toggleTheme
            ) {
                val alphaAnimate by animateFloatAsState(
                    targetValue = if (scrollProgress < 0.5f) 0f else scrollProgress,
                    animationSpec = spring(stiffness = Spring.StiffnessLow)
                )
                TopBarContent(
                    session = session,
                    goToEditProfile = goToEditProfile,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .graphicsLayer {
                            translationY = -scrollY
                            alpha = alphaAnimate
                        }
                )
            }
        },
        scrollContent = { scrollProgress, scrollState ->
            val records = viewModel.records.collectAsState(initial = null).value
            if (records.isNullOrEmpty()) {
                Loader(modifier = Modifier.padding(top = headerExpandedHeight))
            } else {
                RecordList(
                    list = records,
                    modifier = Modifier.fillMaxWidth(),
                    scrollState = scrollState,
                    autoPlay = scrollProgress < 0.6f,
                    onFavoriteClick = { recordId, isFavorite ->
                        viewModel.setFavoriteRecord(recordId, isFavorite)
                    }
                )
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

@Preview("Collapsing topBar Header")
@Composable
fun PreviewCollapsingTopBarHeader() {
    ThemedPreview {
        CollapsingTopBarHeader(
            title = "Erick Tijero",
            scrollProgress = 1f,
            expandedHeight = headerExpandedHeight,
            collapsedHeight = headerCollapsedHeight,
            toggleTheme = {}
        ) {}
    }
}
