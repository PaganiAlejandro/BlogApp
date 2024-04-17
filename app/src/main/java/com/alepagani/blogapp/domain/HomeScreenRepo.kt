package com.alepagani.blogapp.domain

import com.alepagani.blogapp.core.Resource
import com.alepagani.blogapp.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPost(): Resource<List<Post>>
}