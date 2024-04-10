package com.anton.sber.data.repository

interface LogRepository {
    fun logNonFatalCrash(throwable: Throwable)
}