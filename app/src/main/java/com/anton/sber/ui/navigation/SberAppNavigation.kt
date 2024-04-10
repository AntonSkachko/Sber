package com.anton.sber.ui.navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.anton.sber.ui.screen.achievement.AchievementDestination
import com.anton.sber.ui.screen.achievement.AchievementScreen
import com.anton.sber.ui.screen.achievement.AchievementViewModel
import com.anton.sber.ui.screen.control_panel.ControlPanelDestination
import com.anton.sber.ui.screen.control_panel.ControlPanelScreen
import com.anton.sber.ui.screen.splash.SplashScreen
import com.anton.sber.ui.screen.splash.SplashScreenDestination

@Composable
fun SberAppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    achievementViewModel: AchievementViewModel
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination.route,
        modifier = modifier
    ) {

        composable(route = SplashScreenDestination.route) {
            SplashScreen(openAndPopUp = { route, popUp ->
                navController.navigate(route) {
                    launchSingleTop = true
                    popUpTo(popUp) { inclusive = true }
                }
            })
        }
        composable(route = AchievementDestination.route) {
            AchievementScreen(
                achievementViewModel = achievementViewModel
            )
        }
        composable(route = ControlPanelDestination.route) {
            ControlPanelScreen()
        }
    }
}