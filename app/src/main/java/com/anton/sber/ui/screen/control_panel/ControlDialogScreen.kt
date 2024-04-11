package com.anton.sber.ui.screen.control_panel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.anton.sber.R
import com.anton.sber.data.model.enums.Period
import com.anton.sber.data.model.enums.Type
import com.anton.sber.ui.navigation.NavigationDestination

object ControlPanelDestination : NavigationDestination {
    override val route: String = "control_panel"
    override val titleRes: Int = R.string.control_panel_destination
    val icon: ImageVector = Icons.Filled.Settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlDialogScreen(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    controlPanelViewModel: ControlPanelViewModel = hiltViewModel()
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOptionText by rememberSaveable { mutableStateOf(Type.RegularTransaction) }

//    Dialog(
//        onDismissRequest = onDismissRequest,
//
//    ) {

    Card(
        modifier = modifier.fillMaxSize()
    ) {
        Column {

            Text(text = "Обновить задачи:")
            Row {
                Button(
                    onClick = {
                        controlPanelViewModel.updateTasks(Period.Month)
                    }
                ) {
                    Text(text = "Месяца")
                }
                Button(
                    onClick = {
                        controlPanelViewModel.updateTasks(Period.Day)
                    }
                ) {
                    Text(text = "Дня")
                }
            }
            Row {

                
                Button(onClick = { expanded = true }) {
                    Text(text = "Выберите транзакцию")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    Type.entries.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it.russianName) },
                            onClick = {
                                selectedOptionText = it
                                expanded = false
                            })
                    }
                }
            }
            Button(
                onClick = {
                    controlPanelViewModel.incrementProgress(selectedOptionText)
                }
            ) {
                Text(text = "Произвести транзакцию")
            }
        }
    }
}
//}
