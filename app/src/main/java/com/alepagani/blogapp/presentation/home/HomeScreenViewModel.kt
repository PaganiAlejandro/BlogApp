package com.alepagani.blogapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.alepagani.blogapp.core.Result
import com.alepagani.blogapp.data.model.Post
import com.alepagani.blogapp.domain.Home.HomeScreeRepoImpl
import com.alepagani.blogapp.domain.Home.HomeScreenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repo: HomeScreenRepo): ViewModel() {

    fun fetchLatestPosts() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        runCatching {
            repo.getLatestPost()
        }.onSuccess { result ->
            emit(result)
        }.onFailure {
            emit(Result.Failure(Exception(it.message)))
        }
    }

    // esto es el metodo de arriba, pero con stateflow
    val latestPost: StateFlow<Result<List<Post>>> = flow {
        runCatching {
            repo.getLatestPost()
        }.onSuccess { result ->
            emit(result)
        }.onFailure {
            emit(Result.Failure(Exception(it)))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading()
    )

    fun registerLikeButtonState(postId: String, liked: Boolean) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        kotlin.runCatching {
            repo.registerLike(postId, liked)
        }.onSuccess {
            emit(Result.Success(Unit))
        }.onFailure {
            emit(Result.Failure(Exception(it.message)))
        }
    }
}

// class HomeScreenViewModelFactory(private val repo: HomeScreenRepo): ViewModelProvider.Factory {
//     override fun <T : ViewModel> create(modelClass: Class<T>): T {
//         return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
//     }
// }