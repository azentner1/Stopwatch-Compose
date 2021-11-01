package com.hubstaff.challenge.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hubstaff.challenge.screen.login.repo.LoginRepository
import com.hubstaff.shared_prefs.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

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