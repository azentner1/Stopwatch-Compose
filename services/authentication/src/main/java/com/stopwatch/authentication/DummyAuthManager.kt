package com.stopwatch.authentication

import com.stopwatch.authentication.data.AuthResult
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

internal class DummyAuthManager @Inject constructor() : AuthenticationManager {

    private val LOGIN_DELAY = 500L
    private var attemptsCounter = 0

    private val _authState = MutableStateFlow(false)

    override val authState: StateFlow<Boolean>
        get() = _authState


    override suspend fun login(email: String, password: String): AuthResult {
        delay(LOGIN_DELAY)
        attemptsCounter++
        return if (attemptsCounter == 1) {
            _authState.value = false
            AuthResult(false, "next attempt will succeed, try again please")
        } else {
            _authState.value = true
            AuthResult(true)
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthManagerProvider {

    @Singleton
    @Binds
    internal abstract fun getAuthManger(
        impl: DummyAuthManager,
    ): AuthenticationManager
}
