package com.anton.sber.ui.screen.splash

import androidx.compose.runtime.mutableStateOf
import com.anton.sber.data.repository.AccountRepository
import com.anton.sber.data.repository.LogRepository
import com.anton.sber.data.repository.UserTaskRepository
import com.anton.sber.ui.screen.SberViewModel
import com.anton.sber.ui.screen.achievement.AchievementDestination
import com.anton.sber.ui.screen.control_panel.ControlPanelDestination
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val userTaskRepository: UserTaskRepository,
    logRepository: LogRepository
) : SberViewModel(logRepository) {
    val showError = mutableStateOf(false)

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        showError.value = false
        if (accountRepository.hasUser) openAndPopUp(
            AchievementDestination.route, ControlPanelDestination.route
        )
        else createAnonymousAccount(openAndPopUp)
        loadInitialData()
    }

    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackbar = false) {
            try {
                accountRepository.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
            openAndPopUp(AchievementDestination.route, ControlPanelDestination.route)
        }
    }

    private fun loadInitialData() {
        launchCatching {
            userTaskRepository.addOrUpdateUserTaskFromTasks()
        }
    }
}
