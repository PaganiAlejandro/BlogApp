package com.alepagani.blogapp.data.remote.camera

import android.graphics.Bitmap
import android.net.Uri
import com.alepagani.blogapp.data.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.Date
import java.util.UUID

class CameraDataSource {
    suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val ramdomName = UUID.randomUUID().toString()
        val imageRef = FirebaseStorage.getInstance().reference.child("${user?.uid}/posts/${ramdomName}")
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        // upload the image and get url from firebase
        val downloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()
        user?.displayName?.let {
            Post(
                profile_name = it.toString(),
                profile_picture = user?.photoUrl.toString(),
                post_image = downloadUrl,
                post_description = description,
                uid = user.uid.toString()
                )
        }?.let {
            FirebaseFirestore.getInstance().collection("posts").add(
                it
            )
        }
    }
}