package com.anton.sber.data.repository.imp

import android.content.Context

class ScoreManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("ScorePrefs", Context.MODE_PRIVATE)
    private val keyScore = "score"

    fun saveScore(score: Float) {
        sharedPreferences.edit().putFloat(keyScore, score).apply()
    }

    fun loadScore(): Float {
        return sharedPreferences.getFloat(keyScore, 0f)
    }

    fun updateAmountOfPoints(pointsToAdd: Float) {
        val currentScore = loadScore()
        val updatedScore = currentScore + pointsToAdd
        saveScore(updatedScore)

    }
}