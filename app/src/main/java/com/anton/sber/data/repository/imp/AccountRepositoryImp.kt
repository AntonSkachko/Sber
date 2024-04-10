package com.anton.sber.data.repository.imp

import android.content.SharedPreferences
import com.anton.sber.data.model.User
import com.anton.sber.data.repository.AccountRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountRepositoryImp @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AccountRepository {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let {
                        User(it.uid, it.isAnonymous)
                    } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun createAnonymousAccount() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val result = auth.signInAnonymously().await()
            val user = result.user
            if (user != null) {
                // Сохранить уникальный uid в Firestore вместе с другими данными пользователя
                val userDocument = firestore.collection("users").document(user.uid)
                val userData = hashMapOf(
                    "anonymous" to true, // Можно добавить дополнительные данные, если нужно
                    // Другие данные пользователя
                )
                userDocument.set(userData).await()
            } else {
                throw IllegalStateException("Failed to create anonymous user.")
            }
        }
    }
}