package com.anton.sber.data.model

import com.google.firebase.firestore.DocumentId

data class UserTask(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val progress: Int = 0,
    val maxProgress: Int = 0,
    val type: String = "",
    val score: Double = 0.0,
    val period: String = "day",
    val userId: String = ""
)