package com.hubstaff.challenge.screen.login.data

import com.netsoft.android.authentication.AuthenticationManager
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val authenticationManager: AuthenticationManager
) : LoginDataSource {

    override suspend fun loginState(): StateFlow<Boolean> {
        return authenticationManager.loginState
    }

    override suspend fun login(email: String, password: String) {
        authenticationManager.login(email, password)
    }
}