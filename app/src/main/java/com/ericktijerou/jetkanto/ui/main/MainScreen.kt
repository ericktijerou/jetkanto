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
package com.ericktijerou.jetkanto.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ericktijerou.jetkanto.ui.community.CommunityScreen
import com.ericktijerou.jetkanto.ui.component.Pager
import com.ericktijerou.jetkanto.ui.component.PagerState
import com.ericktijerou.jetkanto.ui.explore.ExploreScreen
import com.ericktijerou.jetkanto.ui.notification.NotificationListScreen
import com.ericktijerou.jetkanto.ui.profile.ProfileScreen
import com.ericktijerou.jetkanto.ui.song.SongListScreen
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.theme.Teal500
import com.ericktijerou.jetkanto.ui.util.MainSection
import com.ericktijerou.jetkanto.ui.util.ThemedPreview

@Composable
fun MainScreen() {
    val sections = listOf(
        MainSection.Community,
        MainSection.Explore,
        MainSection.Song,
        MainSection.Notification,
        MainSection.Profile
    )
    val pagerState = remember { PagerState() }
    Scaffold(
        bottomBar = {
            HomeBottomNavigation(
                sections = sections,
                pagerState = pagerState
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        HomeViewPager(
            items = sections,
            pagerState = pagerState,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun HomeViewPager(
    items: List<MainSection>,
    modifier: Modifier = Modifier,
    pagerState: PagerState = remember { PagerState() },
) {
    pagerState.maxPage = (items.size - 1).coerceAtLeast(0)
    Pager(
        state = pagerState,
        modifier = modifier,
        userInputEnabled = false
    ) {
        when (items[page]) {
            is MainSection.Community -> CommunityScreen()
            is MainSection.Explore -> ExploreScreen()
            is MainSection.Song -> SongListScreen()
            is MainSection.Notification -> NotificationListScreen()
            is MainSection.Profile -> ProfileScreen()
        }
    }
}

@Composable
fun HomeBottomNavigation(
    sections: List<MainSection>,
    pagerState: PagerState = remember { PagerState() }
) {
    BottomNavigation {
        sections.forEachIndexed { index, section ->
            BottomNavigationItem(
                icon = { Icon(imageVector = section.icon, contentDescription = section.route) },
                selected = pagerState.currentPage == index,
                onClick = { pagerState.currentPage = index },
                selectedContentColor = Teal500,
                alwaysShowLabel = false,
                unselectedContentColor = KantoTheme.customColors.textSecondaryColor
            )
        }
    }
}

@Preview("Main screen")
@Composable
fun PreviewMainScreen() {
    ThemedPreview {
        MainScreen()
    }
}

@Preview("Main screen dark")
@Composable
fun PreviewMainScreenDark() {
    ThemedPreview(darkTheme = true) {
        MainScreen()
    }
}
