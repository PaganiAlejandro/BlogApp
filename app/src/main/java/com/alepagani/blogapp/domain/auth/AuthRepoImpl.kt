package com.alepagani.blogapp.domain.auth

import com.alepagani.blogapp.data.remote.login.LoginDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private val dataSource:LoginDataSource): AuthRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? = dataSource.signIn(email, password)
    override suspend fun signUp(email: String, password: String, usuario: String): FirebaseUser? = dataSource.sigUp(email, password, usuario)
}