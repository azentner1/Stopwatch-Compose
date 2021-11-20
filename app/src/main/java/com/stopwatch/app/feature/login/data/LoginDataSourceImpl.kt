package com.stopwatch.app.feature.login.data

import com.stopwatch.authentication.AuthenticationManager
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val authenticationManager: AuthenticationManager
) : LoginDataSource {

    override suspend fun loginState(): StateFlow<Boolean> {
        return authenticationManager.authState
    }

    override suspend fun login(email: String, password: String) {
        authenticationManager.login(email, password)
    }
}