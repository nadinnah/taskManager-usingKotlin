package com.example.taskmanager.tasks.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.taskmanager.tasks.domain.model.Task
import com.example.taskmanager.tasks.domain.repository.StorageRepository
import com.google.firebase.auth.FirebaseUser

class DetailScreen(
    private val repository: StorageRepository
) : ViewModel() {
    var detailUiState by mutableStateOf(DetailUIState())
        private set

    private val hasUser:Boolean
        get()=repository.hasUser()

    private val user:FirebaseUser?
        get()= repository.user

    fun onColorChange(colorIndex: Int) {
        detailUiState = detailUiState.copy(colorIndex = colorIndex)
    }

    fun onTaskTitleChange(taskTitle: String) {
        detailUiState = detailUiState.copy(taskTitle = taskTitle)
    }

    fun onTaskChange(task: String) {
        detailUiState = detailUiState.copy(task = task)
    }

    fun addTask(){
        if(hasUser){
            repository.addTask(
                userId = user!!.uid,
                taskTitle = detailUiState.taskTitle,

        }
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
    val category: String = ""


)