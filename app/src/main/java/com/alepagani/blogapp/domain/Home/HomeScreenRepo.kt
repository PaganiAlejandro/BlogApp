package com.alepagani.blogapp.domain.Home

import com.alepagani.blogapp.core.Result
import com.alepagani.blogapp.data.model.Post
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepo {
    suspend fun getLatestPost(): Result<List<Post>>

    suspend fun registerLike(postId: String, liked: Boolean)
}