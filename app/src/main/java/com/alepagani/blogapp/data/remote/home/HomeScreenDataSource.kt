package com.alepagani.blogapp.data.remote.home

import com.alepagani.blogapp.core.Result
import com.alepagani.blogapp.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource() {

    suspend fun getLatestPost(): Result<List<Post>> {
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").get().await()
        for (post in querySnapshot.documents){
            post.toObject(Post::class.java)?.let {
                postList.add(it)
            }
        }
        return Result.Success(postList)
    }
}