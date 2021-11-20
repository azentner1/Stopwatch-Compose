package com.stopwatch.app.feature.timer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stopwatch.android.timer.data.TimerState
import com.stopwatch.android.timer.repo.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
@HiltViewModel
class TimerViewModel @Inject constructor(
    private val timerRepository: TimerRepository
) : ViewModel() {

    var timerState by mutableStateOf(TimerState(value = 0, isRunning = false))
        private set

    init {
        initObserver()
    }

    private fun initObserver() {
        viewModelScope.launch {
            timerRepository.observeTimer().collect {
                timerState = it
            }
        }
    }

    fun startTimer() {
        timerRepository.startTimer(TIMER_INTERVAL)
    }

    fun stopTimer() {
        timerRepository.stopTimer()
    }

    fun shouldResumeTimerOnStart(): Boolean {
        return timerRepository.shouldResumeTimerOnStart()
    }

    fun formattedElapsedTime(value: Long): String {
        val millis = value * 1000
        return String.format(
            "%02d : %02d : %02d",
            TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(millis)
            ),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millis)
            )
        )
    }


    companion object {
        private const val TIMER_INTERVAL = 1000L
    }

}