package com.example.taskmanager.home.domain.repository

import arrow.core.Either
import com.example.taskmanager.home.domain.model.NetworkError
import com.example.taskmanager.home.domain.model.Task

interface TaskRepository {
    //no implementation inside domain layer that's why interface
    //depend on abstraction not concretion principle
    suspend fun getTask(): Either<NetworkError,List<Task>>


}