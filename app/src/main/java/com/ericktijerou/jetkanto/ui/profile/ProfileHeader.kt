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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericktijerou.jetkanto.R
import com.ericktijerou.jetkanto.ui.component.Avatar
import com.ericktijerou.jetkanto.ui.component.Loader
import com.ericktijerou.jetkanto.ui.entity.UserView
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.theme.PurpleDark
import com.ericktijerou.jetkanto.ui.theme.PurpleOpaque
import com.ericktijerou.jetkanto.ui.util.MockDataHelper
import com.ericktijerou.jetkanto.ui.util.ThemedPreview
import dev.chrisbanes.accompanist.coil.CoilImage
import java.io.File

@Composable
fun TopBarContent(modifier: Modifier = Modifier, session: UserView, goToEditProfile: () -> Unit) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Avatar(
            color = Color.White,
            strokeWidth = 2.dp,
            modifier = Modifier.size(112.dp)
        ) { imageSize ->
            val imageData = if (session.localAvatarPath.isNotEmpty()) {
                File(session.localAvatarPath)
            } else {
                session.avatar
            }
            CoilImage(
                data = imageData,
                contentDescription = stringResource(R.string.label_avatar),
                fadeIn = true,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(imageSize),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = session.name,
            style = KantoTheme.typography.h6.copy(fontWeight = FontWeight.Black),
            color = Color.White,
            modifier = Modifier.padding(top = 12.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = session.username,
            style = KantoTheme.typography.body2,
            color = Color.White.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
        Text(
            text = session.bio,
            style = KantoTheme.typography.body2,
            color = Color.White.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        if (session.id.isEmpty()) {
            Loader(
                strokeWidth = 2.dp,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(26.dp)
            )
        } else {
            TextButton(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = 26.dp
                    )
                    .height(26.dp),
                onClick = goToEditProfile,
                shape = CircleShape,
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.White,
                    contentColor = PurpleDark
                )
            ) {
                Text(
                    text = stringResource(R.string.label_edit_profile),
                    style = KantoTheme.typography.body2.copy(
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .height(50.dp)
                .fillMaxWidth()
                .background(color = PurpleOpaque, shape = RoundedCornerShape(16.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val indicators = listOf(
                Indicator(
                    value = session.followers,
                    label = stringResource(R.string.label_followers)
                ),
                Indicator(
                    value = session.followed,
                    label = stringResource(R.string.label_followed)
                ),
                Indicator(value = session.views, label = stringResource(R.string.label_views))
            )
            indicators.forEach {
                IndicatorProfile(modifier = Modifier.weight(1f), it)
            }
        }
    }
}

@Composable
fun IndicatorProfile(modifier: Modifier = Modifier, indicator: Indicator) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = indicator.value.toString(),
            style = KantoTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
        Text(
            text = indicator.label,
            style = KantoTheme.typography.body2,
            color = Color.White.copy(alpha = 0.6f)
        )
    }
}

data class Indicator(
    val value: Int,
    val label: String
)

@Preview("Profile header")
@Composable
fun PreviewProfileHeader() {
    ThemedPreview {
        TopBarContent(session = MockDataHelper.session, goToEditProfile = {})
    }
}

@Preview("Profile header dark")
@Composable
fun PreviewProfileHeaderDark() {
    ThemedPreview(darkTheme = true) {
        TopBarContent(session = MockDataHelper.session, goToEditProfile = {})
    }
}
