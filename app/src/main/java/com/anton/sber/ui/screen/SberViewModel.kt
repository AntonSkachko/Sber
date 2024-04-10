package com.anton.sber.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anton.sber.common.snackbar.SnackbarManager
import com.anton.sber.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.anton.sber.data.repository.LogRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class SberViewModel(
    private val logRepository: LogRepository
): ViewModel() {
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logRepository.logNonFatalCrash(throwable)
            },
            block = block
        )
}