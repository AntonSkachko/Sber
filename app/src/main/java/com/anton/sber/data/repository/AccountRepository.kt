package com.anton.sber.data.repository

import com.anton.sber.data.model.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun createAnonymousAccount()
    suspend fun updateUserAmountOfPoints(amountOfPoints: Double)

}