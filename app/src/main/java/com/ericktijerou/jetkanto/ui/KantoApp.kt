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
package com.ericktijerou.jetkanto.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.ericktijerou.jetkanto.ui.editprofile.EditProfileScreen
import com.ericktijerou.jetkanto.ui.main.MainScreen
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.util.Screens
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@Composable
fun KantoApp(darkTheme: Boolean, toggleTheme: () -> Unit) {
    ProvideWindowInsets {
        KantoTheme(darkTheme = darkTheme) {
            val navController = rememberNavController()
            NavHost(navController, startDestination = Screens.Main.route) {
                composable(Screens.Main.route) {
                    MainScreen(
                        goToEditProfile = {
                            navController.navigate(route = Screens.EditProfile.route)
                        },
                        toggleTheme = toggleTheme
                    )
                }
                composable(Screens.EditProfile.route) {
                    EditProfileScreen(onBackPressed = { navController.navigateUp() })
                }
            }
        }
    }
}
