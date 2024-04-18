package com.alepagani.blogapp.presentation.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.alepagani.blogapp.core.Result
import com.alepagani.blogapp.domain.auth.AuthRepo
import com.alepagani.blogapp.domain.camera.CameraRepo
import com.alepagani.blogapp.presentation.auth.AuthViewModel
import kotlinx.coroutines.Dispatchers

class CameraViewModel(private val repo: CameraRepo): ViewModel() {

    fun uploadPhoto(imageBitmap: Bitmap, description: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.uploadPhoto(imageBitmap, description)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class CameraViewModelFactory(private val repo: CameraRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CameraViewModel(repo) as T
    }
}