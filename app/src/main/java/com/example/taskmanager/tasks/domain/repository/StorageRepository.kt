package com.example.taskmanager.tasks.domain.repository

import com.example.taskmanager.tasks.domain.model.Task
import com.example.taskmanager.tasks.domain.model.TaskCategory
import com.example.taskmanager.tasks.domain.model.TaskStatus
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val TASKS_COLLECTION_REF = "tasks"

class StorageRepository() {

    val user = Firebase.auth.currentUser

    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val tasksRef = Firebase.firestore.collection(TASKS_COLLECTION_REF)

    //returning Flow: works for data changes
    fun getUserTasks(userId: String): Flow<Resources<List<Task>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {
            snapshotStateListener = tasksRef.orderBy("createdAt").whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val tasks =
                            snapshot.toObjects(Task::class.java) // deserialize data that has been returned
                        Resources.Success(data = tasks)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)

                }

        } catch (e: Exception) {
            trySend(Resources.Error(e?.cause))
            e.printStackTrace()

        }

        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    fun getTask(taskId: String, onError: (Throwable?) -> Unit, onSuccess: (Task?) -> Unit) {
        tasksRef.document(taskId).get().addOnSuccessListener {
            onSuccess.invoke(it.toObject(Task::class.java))
        }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun addTask(
        taskTitle: String,
        status: TaskStatus,
        createdAt: Timestamp,
        color: Int = 0,
        dueDate: Timestamp,
        isDeleted: Boolean = false,
        userId: String,
        taskCategory: TaskCategory,
        onComplete: (Boolean) -> Unit
    ) {
        val documentId= tasksRef.document().id
        val task= Task(
            documentId= documentId,
            taskTitle,
            status,
            createdAt,
            colorIndex= color,
            dueDate,
            isDeleted,
            userId,
            taskCategory
            )

        tasksRef.document(documentId).set(task).addOnCompleteListener{
            result-> onComplete.invoke(result.isSuccessful)
        }

    }

    //fun delete
    fun deleteTask(taskId: String, onComplete: (Boolean) -> Unit){
        tasksRef.document(taskId).delete().addOnCompleteListener{
            onComplete.invoke(it.isSuccessful)
        }
    }

    fun updateTask(taskId:String, taskTitle:String, color: Int, category: TaskCategory, status:TaskStatus, dueDate:Timestamp, onResult:(Boolean) -> Unit){

        val updateData= hashMapOf<String, Any>(
            "taskTitle" to taskTitle,
            "colorIndex" to color,
            "category" to category,
            "status" to status,
            "dueDate" to dueDate
        )

        tasksRef.document(taskId).update(updateData).addOnCompleteListener {
            onResult(it.isSuccessful)
        }

    }
}

//manages the state of getting the data
sealed class Resources<T>(val data: T? = null, val throwable: Throwable? = null) {
    class Loading<T> : Resources<T>()
    class Success<T>(data: T) : Resources<T>(data = data)
    class Error<T>(throwable: Throwable?) : Resources<T>(throwable = throwable)
}