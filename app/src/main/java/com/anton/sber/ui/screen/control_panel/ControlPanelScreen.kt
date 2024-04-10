package com.anton.sber.ui.screen.control_panel

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.anton.sber.R
import com.anton.sber.data.model.enums.Type
import com.anton.sber.ui.navigation.NavigationDestination
import com.anton.sber.ui.screen.achievement.AchievementViewModel

object ControlPanelDestination : NavigationDestination {
    override val route: String = "control_panel"
    override val titleRes: Int = R.string.control_panel_destination
    val icon: ImageVector = Icons.Filled.Settings
}

@Composable
fun ControlPanelScreen(
    modifier: Modifier = Modifier,
    controlPanelViewModel: ControlPanelViewModel = hiltViewModel()
) {
    Column {

        Button(
            onClick = { controlPanelViewModel.updateTasks() }
        ) {
            Text(text = "Update Tasks")
        }
        Button(
            onClick = { controlPanelViewModel.incrementProgress(Type.RegularTransaction) }
        ) {
            Text(text = "incrementProgress ReqularTransaction")
        }
    }
}

