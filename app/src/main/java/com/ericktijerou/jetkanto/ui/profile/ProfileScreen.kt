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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericktijerou.jetkanto.R
import com.ericktijerou.jetkanto.ui.component.Avatar
import com.ericktijerou.jetkanto.ui.component.CollapsingScrollTopBar
import com.ericktijerou.jetkanto.ui.component.CollapsingTopBarContent
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.theme.PurpleOpaque
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun ProfileScreen() {
    CollapsingScrollTopBar(
        expandedHeight = headerExpandedHeight,
        header = { scrollProgress ->
            CollapsingTopBarContent(
                title = "Foo",
                scrollProgress = scrollProgress,
                expandedHeight = headerExpandedHeight,
                collapsedHeight = headerCollapsedHeight,
                onCloseClicked = { },
                topBarTintCollapsedColor = KantoTheme.customColors.textPrimaryColor,
                topBarTintExpandedColor = Color.White
            ) {
                TopBarContent(modifier = Modifier.fillMaxSize())
            }
        },
        scrollContent = { scrollState ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .wrapContentHeight()
            ) {
                Box(Modifier.padding(top = headerExpandedHeight))
                Text(
                    text = puppyDescription,
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.Light
                    ),
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun TopBarContent(modifier: Modifier = Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Avatar(color = Color.White, strokeWidth = 2.dp) {
            CoilImage(
                data = "https://avatars.githubusercontent.com/u/17746153?s=400&u=07209b0bc7226e4196dc4488b0dcab92e092027c&v=4",
                contentDescription = stringResource(R.string.label_avatar),
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
            )
        }
        Text(
            text = "Erick Tijero",
            style = KantoTheme.typography.h6.copy(fontWeight = FontWeight.Black),
            color = Color.White,
            modifier = Modifier.padding(top = 12.dp)
        )
        Text(
            text = "@ericktijerou",
            style = KantoTheme.typography.body2,
            color = Color.White.copy(alpha = 0.6f)
        )
        Text(
            text = "Bio",
            style = KantoTheme.typography.body2,
            color = Color.White.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 8.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .height(24.dp)
                .background(color = Color.White, shape = CircleShape)
        ) {
            Text(
                text = stringResource(R.string.label_edit_profile),
                style = KantoTheme.typography.body2.copy(
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 24.dp)
                .height(50.dp)
                .fillMaxWidth()
                .background(color = PurpleOpaque, shape = RoundedCornerShape(16.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0..2) {
                IndicatorProfile(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun IndicatorProfile(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "0",
            style = KantoTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
        Text(
            text = "Followers",
            style = KantoTheme.typography.body2,
            color = Color.White.copy(alpha = 0.6f)
        )
    }
}

private val headerExpandedHeight = 330.dp
private val headerCollapsedHeight = 56.dp

private const val puppyDescription =
    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like). Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like). Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)."
