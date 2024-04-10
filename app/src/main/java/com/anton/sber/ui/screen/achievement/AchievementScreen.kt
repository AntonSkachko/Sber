package com.anton.sber.ui.screen.achievement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anton.sber.R
import com.anton.sber.data.model.User
import com.anton.sber.data.model.UserTask
import com.anton.sber.ui.navigation.NavigationDestination
import com.anton.sber.ui.theme.SberTheme

object AchievementDestination : NavigationDestination {
    override val route: String = "achievement"
    override val titleRes: Int = R.string.achievement_destination
    val icon: ImageVector = Icons.Filled.Home
}

/**
 * Entry route for achievement screen
 */
@Composable
fun AchievementScreen(
    achievementViewModel: AchievementViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

//    val uiState by achievementViewModel.achievementUiState.collectAsState()
    val user = achievementViewModel.user.collectAsStateWithLifecycle(User())
    val monthlyTaskList = achievementViewModel.monthlyTaskList.collectAsStateWithLifecycle(emptyList())
    val dailyTaskList = achievementViewModel.dailyTaskList.collectAsStateWithLifecycle(emptyList())


    var selectedList by rememberSaveable { mutableStateOf(monthlyTaskList) }
    var selectedScreenIsMonth by rememberSaveable { mutableStateOf(true) }

//    LaunchedEffect(Unit) {
//        achievementViewModel.loadInitialData()
//    }

    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {

        UserInformation(
            amountOfPoints = user.value.amountOfPoints,
            bankAccount = user.value.bankAccount
        )
        TaskSelectionButtons(
            onMonthButtonClick = {
                selectedList = monthlyTaskList
                selectedScreenIsMonth = true
            },
            onDayButtonClick = {
                selectedList = dailyTaskList
                selectedScreenIsMonth = false
            },
            currentListIsMonth = selectedScreenIsMonth
        )

        TaskList(tasks = selectedList.value)
    }
}

@Composable
private fun TaskList(
    tasks: List<UserTask>?,
    modifier: Modifier = Modifier
) {
    if (!tasks.isNullOrEmpty()) {
        // todo hardcoded
        Text(text = "28 дней")
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.padding_medium)
            ),
            modifier = modifier.fillMaxSize(),
            // todo try with it
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium))
        ) {
            items(tasks, key = { item -> item.id }) {
                TaskItem(
                    task = it,
                )
            }
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.lack_of_task),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun TaskSelectionButtons(
    currentListIsMonth: Boolean = true,
    onMonthButtonClick: () -> Unit,
    onDayButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (currentListIsMonth) {
            Button(
                onClick = onMonthButtonClick,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(
                    dimensionResource(id = R.dimen.button_shape)
                )
            ) {
                Text(text = stringResource(R.string.month))
            }
            TextButton(
                onClick = onDayButtonClick,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(
                    dimensionResource(id = R.dimen.button_shape)
                )
            ) {
                Text(text = stringResource(R.string.day))
            }
        } else {
            TextButton(
                onClick = onMonthButtonClick,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(
                    dimensionResource(id = R.dimen.button_shape)
                )
            ) {
                Text(text = stringResource(R.string.month))
            }
            Button(
                onClick = onDayButtonClick,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(
                    dimensionResource(id = R.dimen.button_shape)
                )
            ) {
                Text(text = stringResource(R.string.day))
            }
        }
    }
}


@Composable
private fun UserInformation(
    amountOfPoints: Double,
    bankAccount: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.user_score, amountOfPoints, amountOfPoints)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.CreditCard,
                contentDescription = null
            )
            Text(
                text = bankAccount
            )
        }

    }
}


//@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskItem(
    task: UserTask,
//    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.card_elevation)
        ),
        modifier = modifier,
        shape = RoundedCornerShape(
            dimensionResource(id = R.dimen.card_shape)
        ),
//        onClick = { onItemClick(task.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_low))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TODO add personal icons
//                Icon(
//                    imageVector = Icons.Filled.TaskAlt,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .weight(0.7f)
//
//                )
                Column(
                    modifier = Modifier.weight(4f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = task.name)
                    Text(
                        text = task.description
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "+ ${task.score}")
                    Text(text = "балла")
                }
            }

            TaskLinearIndicator(
                currentProgress = task.progress,
                maxProgress = task.maxProgress
            )
        }
    }
}

@Composable
private fun TaskLinearIndicator(
    modifier: Modifier = Modifier,
    currentProgress: Int,
    maxProgress: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (currentProgress != maxProgress) {
                "$currentProgress / $maxProgress"
            } else {
                stringResource(id = R.string.complete)
            }
        )
        LinearProgressIndicator(
            progress = (currentProgress).toFloat() / (maxProgress).toFloat(),
            modifier = modifier
                .fillMaxWidth()
                .height(5.dp),
            trackColor = Color.LightGray
        )
    }
}

@Composable
@Preview
fun TaskItemPreview() {
    SberTheme {
        TaskItem(
            task = UserTask(
                name = "Кофебрейк",
                description = "Сделайте 3 транзакции от 10 BYN в любимой кофейне.",
                progress = 1,
                maxProgress = 3,
                score = 0.2,
                type = "cafe",
                period = "month"
            ),
//            onItemClick = {},
            modifier = Modifier
        )
    }
}


//@Composable
//@Preview(showSystemUi = true)
//fun AchievementPreview() {
//    SberTheme {
//        AchievementScreen()
//    }
//}

//@Composable
//@Preview(showBackground = true)
//fun UserInformationPreview() {
//    SberTheme {
//        UserInformation(
//            amountOfPoints = .0,
//            bankAccount = "**1234"
//        )
//    }
//}