package com.anton.sber.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.anton.sber.ui.screen.achievement.AchievementDestination
import com.anton.sber.ui.screen.achievement.AchievementScreen
import com.anton.sber.ui.screen.achievement.AchievementViewModel
import com.anton.sber.ui.screen.control_panel.ControlDialogScreen
import com.anton.sber.ui.screen.control_panel.ControlPanelDestination
import com.anton.sber.ui.screen.defaultScreen.FirstDefaultDestination
import com.anton.sber.ui.screen.defaultScreen.FirstDefaultScreen
import com.anton.sber.ui.screen.defaultScreen.SecondDefaultDestination
import com.anton.sber.ui.screen.defaultScreen.SecondDefaultScreen
import com.anton.sber.ui.screen.splash.SplashScreen
import com.anton.sber.ui.screen.splash.SplashScreenDestination

@Composable
fun SberAppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    achievementViewModel: AchievementViewModel
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }

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
                achievementViewModel = achievementViewModel,
                navigateBack = {
                    navController.navigate(SecondDefaultDestination.route)
                }
            )
        }
        composable(route = ControlPanelDestination.route) {
            ControlDialogScreen(
                onDismissRequest = { showDialog = false }
            )
        }
        composable(route = FirstDefaultDestination.route) {
            FirstDefaultScreen(
                navigateTo = {
                    navController.navigate(SecondDefaultDestination.route)
                }
            )
        }
        composable(route = SecondDefaultDestination.route) {
            SecondDefaultScreen(
                navigateBack = {
                    navController.navigate(FirstDefaultDestination.route)
                },
                navigateToSberAchievement = {
                    navController.navigate(AchievementDestination.route)
                }
            )
        }
    }
}