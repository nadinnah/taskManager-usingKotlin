package com.example.taskmanager.tasks.domain.model

enum class ApiError(val message: String) {
    NetworkError("Network Error"),
    UnknownError("Unknown Error"),
    UnknownResponse("Unknown Response")
}

data class NetworkError(
    val error: ApiError,
    val t: Throwable
)