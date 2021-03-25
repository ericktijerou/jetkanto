package com.ericktijerou.jetkanto.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ericktijerou.jetkanto.ui.main.MainScreen
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.util.Screens
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@Composable
fun KantoApp() {
    ProvideWindowInsets {
        KantoTheme {
            val navController = rememberNavController()
            NavHost(navController, startDestination = Screens.Main.route) {
                composable(Screens.Main.route) { MainScreen() }
            }
        }
    }
}