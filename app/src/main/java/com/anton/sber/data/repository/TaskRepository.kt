package com.anton.sber.data.repository

import com.anton.sber.data.model.Task
import com.anton.sber.data.model.enums.Period
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTaskStream(id: String): Flow<Task?>

    fun getAllTasksByPeriod(period: Period): Flow<List<Task>?>

    fun getAllTasks(): Flow<List<Task>?>
}
