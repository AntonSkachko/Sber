package com.anton.sber.ui.screen.control_panel

import com.anton.sber.data.model.enums.Type
import com.anton.sber.data.repository.LogRepository
import com.anton.sber.data.repository.UserTaskRepository
import com.anton.sber.ui.screen.SberViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ControlPanelViewModel @Inject constructor(
    logRepository: LogRepository,
    private val userTaskRepository: UserTaskRepository
) : SberViewModel(logRepository) {


    fun incrementProgress(type: Type) {
        launchCatching {
            userTaskRepository.incrementUserTaskProgressInFirestore(type)
        }
    }

    fun updateTasks() {
        launchCatching {
            userTaskRepository.addOrUpdateUserTaskFromTasks()
        }
    }
}