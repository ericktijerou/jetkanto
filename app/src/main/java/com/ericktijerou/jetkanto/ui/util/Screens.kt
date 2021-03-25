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
package com.ericktijerou.jetkanto.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String) {
    object Main : Screens("main")
}

sealed class MainSection(val route: String, val icon: ImageVector) {
    object Community : MainSection("user", Icons.Filled.Home)
    object Explore : MainSection("repo", Icons.Filled.PlayCircleOutline)
    object Song : MainSection("repo", Icons.Filled.LibraryMusic)
    object Notification : MainSection("repo", Icons.Filled.Notifications)
    object Profile : MainSection("repo", Icons.Filled.Person)
}
