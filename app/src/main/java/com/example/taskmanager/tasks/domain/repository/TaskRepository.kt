package com.example.taskmanager.tasks.domain.repository

import arrow.core.Either
import com.example.taskmanager.tasks.domain.model.NetworkError
import com.example.taskmanager.tasks.domain.model.Task

interface TaskRepository {
    //no implementation inside domain layer that's why interface
    //depend on abstraction not concretion principle
    suspend fun getTask(): Either<NetworkError,List<Task>>


}