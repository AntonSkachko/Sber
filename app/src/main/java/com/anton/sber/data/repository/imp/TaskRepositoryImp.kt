package com.anton.sber.data.repository.imp

import android.util.Log
import com.anton.sber.data.model.Task
import com.anton.sber.data.model.enums.Period
import com.anton.sber.data.model.enums.Type
import com.anton.sber.data.repository.TaskRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepositoryImp @Inject constructor(
    private val firestore: FirebaseFirestore
) : TaskRepository {

    private val collection
        get() = firestore.collection(TASKS)

    private var currentDayIndex = 0
    private var currentMonthIndex = 0

    private var lastDayTaskId: String = collection.document().id
    private var lastMonthTaskId: String = collection.document().id


    override fun getTaskStream(id: String): Flow<Task?> = flow {
        try {
            val query = collection.document(id).get().await()
            val task = query.toObject(Task::class.java)
            emit(task)
        } catch (e: Exception) {
            emit(null)
        }
    }


//    override fun getAllTasks(): Flow<List<Task>?> = flow {
//        val tasks = mutableListOf<Task>()
//
//        val dayQuery = collection
//            .whereEqualTo(PERIOD_FIELD, Period.Day.name)
//            .limit(1)
//            .get().await()
//
//        val monthQuery = collection
//            .whereEqualTo(PERIOD_FIELD, Period.Month.name)
//            .limit(3)
//            .get().await()
//
//        for (doc in dayQuery.documents) {
//            val task = doc.toObject(Task::class.java)
//            task?.let { tasks.add(it) }
//        }
//        for (doc in monthQuery.documents) {
//            val task = doc.toObject(Task::class.java)
//            task?.let { tasks.add(it) }
//        }
//        emit(tasks)
//    }.catch {
//        emit(mutableListOf())
//    }

    override fun getAllTasks(): Flow<List<Task>?> = flow {
        val tasks = mutableListOf<Task>()

        val dayQuerySnapshot = getNextDayTasks()
        val monthQuerySnapshot = getNextMonthTasks()

        dayQuerySnapshot.documents.forEach { document ->
            val task = document.toObject(Task::class.java)
            task?.let { tasks.add(it) }
        }

        monthQuerySnapshot.documents.forEach { document ->
            val task = document.toObject(Task::class.java)
            task?.let { tasks.add(it) }
        }

        emit(tasks)
    }.catch {
        emit(mutableListOf())
    }

    override fun getAllTasksByPeriod(period: Period): Flow<List<Task>?> = flow {
        val tasks = mutableListOf<Task>()
        val query = collection
            .whereEqualTo(PERIOD_FIELD, period.name)
            .get().await()

        query.documents.shuffled()
            .take(
                if (period == Period.Month) 3
                else 1
            ).forEach { document ->
                val task = document.toObject(Task::class.java)
                task?.let { tasks.add(it) }
            }

        emit(tasks)
    }.catch {
        emit(mutableListOf())
    }


    private suspend fun getNextDayTasks(): QuerySnapshot {
        val dayQuery = collection
            .startAt(lastDayTaskId)
            .whereEqualTo(PERIOD_FIELD, Period.Day.name)
            .limit(1).get().await()

        lastDayTaskId = dayQuery.documents.last().id

        return dayQuery
    }

    private suspend fun getNextMonthTasks(): QuerySnapshot {
        val monthQuery = collection
            .startAt(lastMonthTaskId)
            .whereEqualTo(PERIOD_FIELD, Period.Month.name)
            .limit(3).get().await()

        lastMonthTaskId = monthQuery.documents.last().reference.toString()

        return monthQuery
    }

    companion object {
        private const val TASKS = "tasks"
        private const val MONTH_LIMIT = 3
        private const val DAY_LIMIT = 1
        private const val RANDOM = "rand"
        private const val PERIOD_FIELD = "period"
    }
}