package com.alepagani.blogapp.domain.auth

import com.alepagani.blogapp.data.remote.login.LoginDataSource
import com.google.firebase.auth.FirebaseUser

class LoginRepoImpl(private val dataSource:LoginDataSource): LoginRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? = dataSource.signIn(email, password)
}