package com.alepagani.blogapp.domain.camera

import android.graphics.Bitmap
import com.alepagani.blogapp.data.remote.camera.CameraDataSource
import javax.inject.Inject

class CameraRepoImpl @Inject constructor(private val cameraDataSource: CameraDataSource): CameraRepo {
    override suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {
        cameraDataSource.uploadPhoto(imageBitmap, description)
    }
}