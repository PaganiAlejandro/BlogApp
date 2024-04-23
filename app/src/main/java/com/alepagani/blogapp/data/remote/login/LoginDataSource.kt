package com.alepagani.blogapp.data.remote.login

import android.graphics.Bitmap
import android.net.Uri
import com.alepagani.blogapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class LoginDataSource @Inject constructor() {
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

    suspend fun updateUserProfile(imageBitmap: Bitmap, userName: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val imageRef = FirebaseStorage.getInstance().reference.child("${user?.uid}/profile_picture")
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        // upload the image and get url from firebase
        val downloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(userName)
            .setPhotoUri(Uri.parse(downloadUrl))
            .build()

        user?.updateProfile(profileUpdates)?.await()
    }
}