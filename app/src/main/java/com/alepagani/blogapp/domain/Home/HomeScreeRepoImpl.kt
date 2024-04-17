package com.alepagani.blogapp.domain.Home

import com.alepagani.blogapp.core.Resource
import com.alepagani.blogapp.data.model.Post
import com.alepagani.blogapp.data.remote.home.HomeScreenDataSource

class HomeScreeRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {
    override suspend fun getLatestPost(): Resource<List<Post>> = dataSource.getLatestPost()
}