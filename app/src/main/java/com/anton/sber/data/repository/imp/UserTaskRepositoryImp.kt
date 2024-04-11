package com.anton.sber.data.repository.imp

import android.util.Log
import com.anton.sber.data.model.Task
import com.anton.sber.data.model.UserTask
import com.anton.sber.data.model.enums.Period
import com.anton.sber.data.model.enums.Type
import com.anton.sber.data.model.toUserTask
import com.anton.sber.data.repository.AccountRepository
import com.anton.sber.data.repository.TaskRepository
import com.anton.sber.data.repository.UserTaskRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserTaskRepositoryImp @Inject constructor(
    private val accountRepository: AccountRepository,
    private val firestore: FirebaseFirestore,
    private val taskRepository: TaskRepository
) : UserTaskRepository {

    private val uid = accountRepository.currentUserId

    private val dailyTaskList = taskRepository.getAllTasksByPeriod(Period.Day)
    private val monthlyTaskList = taskRepository.getAllTasksByPeriod(Period.Month)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userDailyTasks: Flow<List<UserTask>>
        get() =
            accountRepository.currentUser.flatMapLatest { user ->
                firestore
                    .collection(USER_TASKS)
                    .whereEqualTo(USER_ID_FIELD, user.id)
                    .whereEqualTo(PERIOD, Period.Day.name)
                    .dataObjects()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userMonthlyTasks: Flow<List<UserTask>>
        get() =
            accountRepository.currentUser.flatMapLatest { user ->
                firestore
                    .collection(USER_TASKS)
                    .whereEqualTo(USER_ID_FIELD, user.id)
                    .whereEqualTo(PERIOD, Period.Month.name)
                    .dataObjects()
            }

    override suspend fun addOrUpdateUserTaskFromTasks(period: Period) {
        try {
            clearUserTasks(period)

            val taskList: List<Task> =
                if (period == Period.Day) dailyTaskList.firstOrNull() ?: emptyList()
                else monthlyTaskList.firstOrNull() ?: emptyList()

            val userTasks = mutableListOf<UserTask>()

            taskList.forEach { task ->
                val userTask = task.toUserTask(accountRepository.currentUserId)
                userTasks.add(userTask)
            }

            userTasks.forEach { userTask ->
                val userTaskId = userTask.id
                if (userTaskId.isNotEmpty()) {
                    firestore
                        .collection(USER_TASKS)
                        .document(userTaskId)
                        .set(userTask).await()
                } else {
                    firestore
                        .collection(USER_TASKS)
                        .add(userTask).await()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getUserTaskFromFirestore() = callbackFlow {
        val query =
            firestore.collection(USER)
                .document(uid).collection(USER_TASKS)
        val snapshotListener =
            query.addSnapshotListener { snapshot, e ->
                val userTask = snapshot?.toObjects(UserTask::class.java)
                trySend(userTask)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addUserTaskToFirestore(userTask: UserTask) {
        val userTaskId = userTask.id ?: NO_VALUE
        firestore.document(userTaskId).set(userTask).await()
    }

    override suspend fun removeUserTaskFromFirestore(userTask: UserTask) {
        val userTaskId = userTask.id
        firestore.document(userTaskId).delete().await()
    }

    override suspend fun incrementUserTaskProgressInFirestore(userTaskId: String) {
        firestore
            .collection(USER_TASKS)
            .document(userTaskId)
            .update(PROGRESS_FIELD, FieldValue.increment(1))
            .await()
    }

    override suspend fun incrementUserTaskProgressInFirestore(type: Type) {

        val querySnapshot = firestore
            .collection(USER_TASKS)
            .whereEqualTo(USER_ID_FIELD, uid)
            .whereEqualTo(TYPE, type.name)
            .get()
            .await()

        querySnapshot.documents.forEach { document ->
            val userTaskId = document.id
            firestore
                .collection(USER_TASKS)
                .document(userTaskId)
                .update(PROGRESS_FIELD, FieldValue.increment(1))
                .await()
        }
    }

    private suspend fun clearUserTasks(period: Period) {
        val userTaskQuery = firestore
            .collection(USER_TASKS)
            .whereEqualTo(PERIOD, period.name)
            .get().await()
        for (doc in userTaskQuery.documents) {
            doc.reference.delete().await()
        }
    }


    companion object {
        private const val USER = "user"
        private const val TYPE = "type"
        private const val PROGRESS_FIELD = "progress"
        private const val USER_ID_FIELD = "userId"
        private const val PERIOD = "period"
        private const val NO_VALUE = ""
        private const val USER_TASKS = "userTasks"
    }
}