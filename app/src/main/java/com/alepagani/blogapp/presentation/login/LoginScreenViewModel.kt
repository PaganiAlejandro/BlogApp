package com.alepagani.blogapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.alepagani.blogapp.core.Resource
import com.alepagani.blogapp.domain.Home.HomeScreenRepo
import com.alepagani.blogapp.domain.auth.LoginRepo
import com.alepagani.blogapp.domain.auth.LoginRepoImpl
import kotlinx.coroutines.Dispatchers

class LoginScreenViewModel(private val repo: LoginRepo): ViewModel() {

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.signIn(email, password)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class LoginScreenViewModelFactory(private val repo: LoginRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginScreenViewModel(repo) as T
    }
}