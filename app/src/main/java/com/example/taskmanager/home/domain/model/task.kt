package com.example.taskmanager.home.domain.model

import java.sql.Timestamp

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
    val id: Int,
    val task: String,
    val status: TaskStatus,
    val createdAt: Timestamp,
    val updatedAt: Timestamp,
    val dueDate: Timestamp,
    val isDeleted: Boolean,
    val userid: Int,
    val category: TaskCategory,
)

