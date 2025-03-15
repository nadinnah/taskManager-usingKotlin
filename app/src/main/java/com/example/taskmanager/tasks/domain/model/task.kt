package com.example.taskmanager.tasks.domain.model

import com.google.firebase.Timestamp

enum class TaskCategory {
    WORK,
    PERSONAL,
    STUDY,
    FITNESS,
    SHOPPING,
    CHORES
}

enum class TaskStatus {
    IN_PROGRESS,
    COMPLETED,
    OVERDUE,
}

data class Task (
    val documentId: String="",
    val taskTitle: String= "",
    val status: TaskStatus= TaskStatus.IN_PROGRESS,
    val createdAt: Timestamp= Timestamp.now(),
    val colorIndex:Int=0,
    val dueDate: Timestamp,
    val isDeleted: Boolean=false,
    val userId: String="",
    val category: TaskCategory= TaskCategory.PERSONAL,
)

