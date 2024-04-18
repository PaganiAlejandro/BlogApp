package com.alepagani.blogapp.data.remote.login

import com.alepagani.blogapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginDataSource {
    suspend fun signIn(email: String, password: String): FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    suspend fun sigUp(email: String, password: String, usuario: String): FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        authResult.user?.uid?.let { uid ->
            FirebaseFirestore.getInstance().collection("users").document(uid).set(User(email = email, username =  usuario, photo_url = "lala")).await()
        }
        return authResult.user
    }
}