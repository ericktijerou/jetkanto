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

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import androidx.navigation.compose.rememberNavController
import com.ericktijerou.jetkanto.ui.community.CommunityScreen
import com.ericktijerou.jetkanto.ui.explore.ExploreScreen
import com.ericktijerou.jetkanto.ui.notification.NotificationListScreen
import com.ericktijerou.jetkanto.ui.profile.ProfileScreen
import com.ericktijerou.jetkanto.ui.song.SongListScreen
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.theme.Teal500
import com.ericktijerou.jetkanto.ui.util.MainSection
import com.ericktijerou.jetkanto.ui.util.ThemedPreview
import com.ericktijerou.jetkanto.ui.util.hiltViewModel

@Composable
fun MainScreen(goToEditProfile: () -> Unit, toggleTheme: () -> Unit) {
    val viewModel: MainViewModel by hiltViewModel()
    val sections = listOf(
        MainSection.Community,
        MainSection.Explore,
        MainSection.Song,
        MainSection.Notification,
        MainSection.Profile
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            HomeBottomNavigation(
                sections = sections,
                navController = navController
            )
        }
    ) { innerPadding ->
        viewModel.syncData()
        val modifier = Modifier.padding(innerPadding)
        NavHost(navController = navController, startDestination = MainSection.Song.route) {
            composable(MainSection.Community.route) { CommunityScreen(modifier = modifier) }
            composable(MainSection.Explore.route) { ExploreScreen(modifier = modifier) }
            composable(MainSection.Song.route) { SongListScreen(modifier = modifier) }
            composable(MainSection.Notification.route) { NotificationListScreen(modifier = modifier) }
            composable(MainSection.Profile.route) {
                ProfileScreen(
                    modifier = modifier,
                    goToEditProfile = goToEditProfile,
                    toggleTheme = toggleTheme
                )
            }
        }
    }
}

@Composable
fun HomeBottomNavigation(
    sections: List<MainSection>,
    navController: NavHostController
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        sections.forEach { section ->
            BottomNavigationItem(
                icon = { Icon(imageVector = section.icon, contentDescription = section.route) },
                selected = currentRoute == section.route,
                onClick = {
                    if (currentRoute != section.route) {
                        navController.navigate(route = section.route) {
                            popUpTo(currentRoute.orEmpty()) { inclusive = true }
                        }
                    }
                },
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
        MainScreen({}, {})
    }
}

@Preview("Main screen dark")
@Composable
fun PreviewMainScreenDark() {
    ThemedPreview(darkTheme = true) {
        MainScreen({}, {})
    }
}
