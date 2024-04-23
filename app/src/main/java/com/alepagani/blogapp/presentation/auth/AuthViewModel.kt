package com.alepagani.blogapp.presentation.auth

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.alepagani.blogapp.core.Result
import com.alepagani.blogapp.domain.auth.AuthRepo
import com.alepagani.blogapp.domain.auth.AuthRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repo: AuthRepoImpl): ViewModel() {

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.signIn(email, password)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun signUp(email: String, password: String, username: String) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.signUp(email, password, username)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun updateUserProfile(imageBitmap: Bitmap, username: String) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.updateProfile(imageBitmap, username)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

// class AuthViewModelFactory(private val repo: AuthRepo): ViewModelProvider.Factory {
//     override fun <T : ViewModel> create(modelClass: Class<T>): T {
//         return AuthViewModel(repo) as T
//     }
// }