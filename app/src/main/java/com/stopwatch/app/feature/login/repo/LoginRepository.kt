package com.stopwatch.app.feature.login.repo

import com.stopwatch.app.feature.login.data.LoginDataSource
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginDataSource: LoginDataSource
) {

    suspend fun loginState(): StateFlow<Boolean> {
        return loginDataSource.loginState()
    }

    suspend fun login(email: String, password: String) {
        loginDataSource.login(email, password)
    }
}