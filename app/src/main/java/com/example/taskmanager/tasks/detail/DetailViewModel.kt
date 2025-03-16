package com.example.taskmanager.tasks.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.taskmanager.tasks.domain.model.Task
import com.example.taskmanager.tasks.domain.model.TaskCategory
import com.example.taskmanager.tasks.domain.model.TaskStatus
import com.example.taskmanager.tasks.domain.repository.StorageRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser

class DetailViewModel(
    private val repository: StorageRepository
) : ViewModel() {
    var detailUiState by mutableStateOf(DetailUIState())
        private set

    private val hasUser: Boolean
        get() = repository.hasUser()

    private val user: FirebaseUser?
        get() = repository.user

    fun onColorChange(colorIndex: Int) {
        detailUiState = detailUiState.copy(colorIndex = colorIndex)
    }

    fun onTaskTitleChange(taskTitle: String) {
        detailUiState = detailUiState.copy(taskTitle = taskTitle)
    }

    fun onTaskChange(task: String) {
        detailUiState = detailUiState.copy(task = task)
    }

    fun addTask() {
        if (hasUser) {
            repository.addTask(
                userId = user!!.uid,
                taskTitle = detailUiState.taskTitle,
                status = detailUiState.selectedTask?.status ?: TaskStatus.IN_PROGRESS,
                createdAt = detailUiState.selectedTask?.createdAt ?: Timestamp.now(),
                color = detailUiState.colorIndex,
                dueDate = detailUiState.selectedTask?.dueDate ?: Timestamp.now(),
                taskCategory = detailUiState.selectedTask?.category ?: TaskCategory.PERSONAL,
                isDeleted = detailUiState.selectedTask?.isDeleted ?: false,

                ) {
                detailUiState = detailUiState.copy(taskAddedStatus = it)
            }
        }
    }

    fun setEditFields(task: Task) {
        detailUiState = detailUiState.copy(
            colorIndex = task.colorIndex,
            taskTitle = task.taskTitle,
            dueDate = task.dueDate.toDate().toString(),
            category = task.category,
            status = task.status,
        )
    }

    fun getTask(taskId: String) {
        repository.getTask(
            taskId = taskId,
            onError = {},
        ) {
            detailUiState = detailUiState.copy(selectedTask = it)
            detailUiState.selectedTask?.let { it1 -> setEditFields(it1) }
        }
    }

    fun updateTask(taskId: String) {
        repository.updateTask(
            taskId = taskId,
            taskTitle = detailUiState.taskTitle,
            color = detailUiState.colorIndex,
            category = detailUiState.category,
            status = detailUiState.status,
            dueDate = Timestamp.now()

        ) {
            detailUiState = detailUiState.copy(updateTaskStatus = it)
        }
    }

    fun resetNoteAddedStatus() {
        detailUiState = detailUiState.copy(
            taskAddedStatus = false,
            updateTaskStatus = false
        )
    }

    fun resetState() {
        detailUiState = DetailUIState()
    }

}

data class DetailUIState(
    val colorIndex: Int = 0,
    val taskTitle: String = "",
    val task: String = "",
    val taskAddedStatus: Boolean = false,
    val updateTaskStatus: Boolean = false,
    val selectedTask: Task? = null,
    val dueDate: String = "",
    val category: TaskCategory = TaskCategory.PERSONAL,
    val status: TaskStatus = TaskStatus.IN_PROGRESS,


    )