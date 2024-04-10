package com.anton.sber.data.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val id: String = "",
    val isAnonymous: Boolean = true,
    val amountOfPoints: Double = .0,
    val bankAccount: String = "*** 1234"
)