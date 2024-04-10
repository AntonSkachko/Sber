package com.anton.sber.ui.screen.achievement

import androidx.compose.runtime.mutableStateListOf
import com.anton.sber.data.model.Task
import com.anton.sber.data.repository.AccountRepository
import com.anton.sber.data.repository.LogRepository
import com.anton.sber.data.repository.UserTaskRepository
import com.anton.sber.ui.screen.SberViewModel
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(
    logRepository: LogRepository,
    private val userTaskRepository: UserTaskRepository,
    private val accountRepository: AccountRepository
) : SberViewModel(logRepository) {

//    private val _achievementUiState = MutableStateFlow(AchievementUiState())
//    val achievementUiState: StateFlow<AchievementUiState> = _achievementUiState

    val monthlyTaskList = userTaskRepository.monthlyTasks
    val dailyTaskList = userTaskRepository.dailyTasks
    val user = accountRepository.currentUser

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        launchCatching {
            userTaskRepository.addOrUpdateUserTaskFromTasks()
        }
    }

}


