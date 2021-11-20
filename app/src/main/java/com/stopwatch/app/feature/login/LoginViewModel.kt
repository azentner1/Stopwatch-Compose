package com.stopwatch.app.feature.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stopwatch.app.feature.login.repo.LoginRepository
import com.stopwatch.shared_prefs.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    var loginState by mutableStateOf(false)
        private set

    init {
        initObserver()
    }

    private fun initObserver() {
        viewModelScope.launch {
            loginRepository.loginState().collect {
                setLoggedIn(it)
                loginState = it
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(username, password)
        }
    }

    private fun setLoggedIn(loggedIn: Boolean) {
        if (loggedIn) {
            sharedPrefs.isLoggedIn = loggedIn
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPrefs.isLoggedIn
    }
}