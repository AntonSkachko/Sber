package com.anton.sber.ui.screen.achievement

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.anton.sber.data.repository.imp.ScoreManager

class ActivityViewModel(application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val scoreManager = ScoreManager(context)

    fun getAmountOfPoints(): Float {
        return scoreManager.loadScore()
    }

    fun incrementAmountOfPoints(amountOfPoints: Double) {
        scoreManager.updateAmountOfPoints(amountOfPoints.toFloat())
    }
}