package com.alepagani.blogapp.domain.Home

import com.alepagani.blogapp.core.Result
import com.alepagani.blogapp.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPost(): Result<List<Post>>
}