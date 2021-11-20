package com.stopwatch.authentication

import com.stopwatch.authentication.data.AuthResult
import kotlinx.coroutines.flow.StateFlow

sealed interface AuthenticationManager {

    val authState: StateFlow<Boolean>

    suspend fun login(email: String, password: String): AuthResult
}

