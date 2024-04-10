package com.anton.sber.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

data class Task(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val maxProgress: Int = 0,
    val type: String = "",
    val score: Double = 0.0,
    val period: String = "Day"
)

fun Task.toUserTask(userId: String) = UserTask(
    id = id,
    name = name,
    description = description,
    progress = 0,
    maxProgress = maxProgress,
    type = type,
    score = score,
    period = period,
    userId = userId
)

//INSERT INTO task (name, description, progress, type, score, period)
//VALUES ("Кофебрейк", "Сделайте 3 транзакции от 10 BYN в любимой кофейне", 0, "cafe", 0.2, "month"),
//("Утренняя зарядка", "Сделайте 3 покупки до 10:00", 0, "transaction", 0.2, "month"),
//("Райское наслаждение", "Сделайте покупку из категории 'Красота и сдоровье' от 30 BYN", 0, "self_care", 0.2, "month"),
//("Башляем", "Совершите 15 транзакций за неделю", 0, "transaction", 0.2, "month")