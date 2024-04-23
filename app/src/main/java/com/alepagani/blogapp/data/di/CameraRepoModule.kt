package com.alepagani.blogapp.data.di

import com.alepagani.blogapp.data.remote.camera.CameraDataSource
import com.alepagani.blogapp.domain.camera.CameraRepo
import com.alepagani.blogapp.domain.camera.CameraRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CameraRepoModule {

    @Provides
    @Singleton
    fun provideCameraRepoImpl(cameraDataSource: CameraDataSource) : CameraRepo {
        return CameraRepoImpl(cameraDataSource)
    }
}