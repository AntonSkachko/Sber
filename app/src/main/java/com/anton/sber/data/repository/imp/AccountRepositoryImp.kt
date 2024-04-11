package com.anton.sber.data.repository.imp

import android.content.SharedPreferences
import android.util.Log
import com.anton.sber.data.model.User
import com.anton.sber.data.repository.AccountRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
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

                val userDocument = firestore.collection(USER).document(user.uid)
                val userData = hashMapOf(
                    IS_ANONYMOUS_FIELD to true,
                    AMOUNT_OF_POINTS_FIELD to .0,
                    BANK_ACCOUNT_FIELD to "*** 1234"
                )
                userDocument.set(userData).await()
            } else {
                throw IllegalStateException("Failed to create anonymous user.")
            }
        }
    }

    override suspend fun updateUserAmountOfPoints(amountOfPoints: Double) {

        Log.i("UpdatingAmountOnRepository", amountOfPoints.toString())
        if (hasUser) {
            firestore
                .collection(USER)
                .document(currentUserId)
                .update(
                    AMOUNT_OF_POINTS_FIELD,
                    FieldValue.increment(amountOfPoints)
                ).await()
        } else {
            throw IllegalStateException("User is not authenticated.")
        }
    }

    companion object {
        private const val USER = "user"
        private const val IS_ANONYMOUS_FIELD = "isAnonymous"
        private const val AMOUNT_OF_POINTS_FIELD = "amountOfPoints"
        private const val BANK_ACCOUNT_FIELD = "bankAccount"
    }
}