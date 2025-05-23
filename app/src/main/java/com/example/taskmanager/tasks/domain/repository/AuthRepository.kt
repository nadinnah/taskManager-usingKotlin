package com.example.taskmanager.tasks.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun hasUser(): Boolean= Firebase.auth.currentUser !=null

    fun getUserId(): String= Firebase.auth.currentUser?.uid.orEmpty()

    suspend fun createUser(userName:String, password:String, onComplete:(Boolean) -> Unit)= withContext(Dispatchers.IO){
        Firebase.auth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener {
            if(it.isSuccessful){
                onComplete.invoke(true)
            }else{
                onComplete.invoke(false)
            }
        }.await()
    }
    suspend fun login(userName:String, password:String, onComplete:(Boolean) -> Unit)= withContext(
        Dispatchers.IO){
        Firebase.auth.signInWithEmailAndPassword(userName, password).addOnCompleteListener {
            if(it.isSuccessful){
                onComplete.invoke(true)
            }else{
                onComplete.invoke(false)
            }
        }.await()
    }
}