package com.anton.sber.ui.screen.achievement

import android.util.Log
import com.anton.sber.data.repository.AccountRepository
import com.anton.sber.data.repository.LogRepository
import com.anton.sber.data.repository.UserTaskRepository
import com.anton.sber.ui.screen.SberViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(
    logRepository: LogRepository,
    private val userTaskRepository: UserTaskRepository,
    private val accountRepository: AccountRepository
) : SberViewModel(logRepository) {

//    private val _achievementUiState = MutableStateFlow(AchievementUiState())
//    val achievementUiState: StateFlow<AchievementUiState> = _achievementUiState

    val monthlyTaskList = userTaskRepository.userMonthlyTasks
    val dailyTaskList = userTaskRepository.userDailyTasks
    val user = accountRepository.currentUser


    fun updateUserAmountOfPoints(amountOfPoints: Double) {

        Log.i("UpdatingAmountOnViewModel", amountOfPoints.toString())
        launchCatching {
            accountRepository.updateUserAmountOfPoints(amountOfPoints)
        }
    }
}


