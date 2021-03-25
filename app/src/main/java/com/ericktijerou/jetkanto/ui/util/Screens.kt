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