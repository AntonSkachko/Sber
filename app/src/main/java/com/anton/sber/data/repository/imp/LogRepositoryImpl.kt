package com.anton.sber.data.repository.imp

import com.anton.sber.data.repository.LogRepository
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import javax.inject.Inject

class LogRepositoryImpl @Inject constructor() : LogRepository {
    override fun logNonFatalCrash(throwable: Throwable) =
        Firebase.crashlytics.recordException(throwable)
}
