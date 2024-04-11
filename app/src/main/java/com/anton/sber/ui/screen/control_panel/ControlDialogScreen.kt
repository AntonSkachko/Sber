package com.anton.sber.ui.screen.control_panel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
    controlPanelViewModel: ControlPanelViewModel
) {
    ControlDialog(
        onDismissRequest = onDismissRequest,
        updateTaskByPeriod = { controlPanelViewModel.updateTasks(it) },
        modifier = modifier,
        incrementProgress = { controlPanelViewModel.incrementProgress(it) }
    )
}

@Composable
fun ControlDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    updateTaskByPeriod: (Period) -> Unit,
    incrementProgress: (Type) -> Unit

) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOption by rememberSaveable { mutableStateOf(Type.RegularTransaction) }
    var selectedPeriod by rememberSaveable { mutableStateOf(Period.Month) }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Card(
            modifier = modifier
                .size(400.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = onDismissRequest) {
                        Icon(Icons.Filled.Close, contentDescription = "Close")
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "Обновить задачи:")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = { updateTaskByPeriod(Period.Month) }

                        ) {
                            Text(text = "Месяца")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { updateTaskByPeriod(Period.Day) }

                        ) {
                            Text(text = "Дня")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Выберите транзакцию")
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Button(
                        onClick = { expanded = true }
                    ) {
                        Text(text = selectedOption.russianName)
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            Type.entries.forEach { type ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedOption = type
                                        expanded = false
                                    },
                                    text = {
                                        Text(text = type.russianName)
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        incrementProgress(selectedOption)
                    }
                ) {
                    Text(text = "Произвести транзакцию")
                }
            }
        }
    }
}

@Preview
@Composable
fun ControlDialogPreview() {
    ControlDialog(onDismissRequest = { /*TODO*/ }, updateTaskByPeriod = {}) {

    }
}
