package com.alepagani.blogapp.data.di

import com.alepagani.blogapp.data.remote.home.HomeScreenDataSource
import com.alepagani.blogapp.domain.Home.HomeScreeRepoImpl
import com.alepagani.blogapp.domain.Home.HomeScreenRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeRepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(dataSource: HomeScreenDataSource): HomeScreenRepo {
        return HomeScreeRepoImpl(dataSource)
    }
}