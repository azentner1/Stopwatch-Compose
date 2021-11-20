package com.stopwatch.app.feature.login.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

interface LoginDataSource {
    suspend fun loginState() : StateFlow<Boolean>
    suspend fun login(email: String, password: String)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginDataSourceProvider {

    @Singleton
    @Binds
    internal abstract fun getLoginDataSource(
        impl: LoginDataSourceImpl,
    ): LoginDataSource
}