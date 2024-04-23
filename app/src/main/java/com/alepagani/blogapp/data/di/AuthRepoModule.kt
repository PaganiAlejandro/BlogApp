package com.alepagani.blogapp.data.di

import com.alepagani.blogapp.data.remote.login.LoginDataSource
import com.alepagani.blogapp.domain.auth.AuthRepo
import com.alepagani.blogapp.domain.auth.AuthRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepoModule {

    @Provides
    @Singleton
    fun provideAuthRepo(dataSource: LoginDataSource) : AuthRepo {
        return AuthRepoImpl(dataSource)
    }
}