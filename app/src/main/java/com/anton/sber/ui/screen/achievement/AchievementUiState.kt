package com.anton.sber.ui.screen.achievement

import com.anton.sber.data.model.Task
import com.anton.sber.data.model.User

data class AchievementUiState (
    val dailyTaskList: List<Task>? = listOf(),
    val monthlyTaskList: List<Task>? = listOf(),
    val userInformation: User = User()
)