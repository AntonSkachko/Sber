package com.anton.sber.data.repository

import com.anton.sber.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTaskStream(id: String): Flow<Task?>

    fun getAllTaskByPeriod(period: String): Flow<List<Task>?>

    fun getAllTasks(): Flow<List<Task>?>
}
