package com.alepagani.blogapp.data.remote.home

import com.alepagani.blogapp.core.Result
import com.alepagani.blogapp.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource() {

    suspend fun getLatestPost(): Flow<Result<List<Post>>> = callbackFlow {
        val postList = mutableListOf<Post>()

        var postReference: Query? = null
        try {
            postReference = FirebaseFirestore.getInstance().collection("posts").orderBy("created_at", Query.Direction.DESCENDING)
        } catch (e: Throwable){
            close(e)
        }

        val suscription = postReference?.addSnapshotListener { value, error ->
            if (value==null) return@addSnapshotListener

            try {
                postList.clear()
                for (post in value.documents){
                    post.toObject(Post::class.java)?.let {
                        postList.add(it)
                    }
                }
            } catch (e: Exception) {
                close(e)
            }

            trySend(Result.Success(postList)).isSuccess
        }
        awaitClose { suscription?.remove() }
    }
}