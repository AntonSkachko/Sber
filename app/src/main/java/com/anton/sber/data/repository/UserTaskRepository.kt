package com.anton.sber.data.repository

import com.anton.sber.data.model.UserTask
import com.anton.sber.data.model.enums.Period
import com.anton.sber.data.model.enums.Type
import kotlinx.coroutines.flow.Flow

interface UserTaskRepository {

    val userDailyTasks: Flow<List<UserTask>>
    val userMonthlyTasks: Flow<List<UserTask>>

    suspend fun addOrUpdateUserTaskFromTasks(period: Period)
    suspend fun getUserTaskFromFirestore(): Flow<MutableList<UserTask>?>
    suspend fun addUserTaskToFirestore(userTask: UserTask)
    suspend fun removeUserTaskFromFirestore(userTask: UserTask)

    suspend fun incrementUserTaskProgressInFirestore(type: Type)
    suspend fun incrementUserTaskProgressInFirestore(userTaskId: String)
}