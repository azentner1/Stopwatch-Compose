package com.stopwatch.android.timer.data

import com.stopwatch.shared_prefs.SharedPrefs
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TimerDataSourceImpl @Inject constructor(
    private val sharedPrefs: SharedPrefs
) : TimerDataSource, TimeTicker {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private lateinit var timerJob: Job

    private var _timerState = MutableStateFlow(getLastTimerState().also {
        currentTimerValue = it.value
    })

    private var currentTimerState: CurrentTimerState = CurrentTimerState.Initial
    private var currentTimerValue: Long = 0

    override val timerState: Flow<TimerState>
        get() = _timerState

    override fun start(period: Long): Boolean {
        sharedPrefs.isTimerRunning = true
        timerJob = initTimer(period)
            .onStart {
                currentTimerState = CurrentTimerState.Running
            }
            .distinctUntilChanged { old, new ->
                old == new
            }.onEach {
                sharedPrefs.lastTimerValue = it
                sharedPrefs.lastTimerTickTime = System.currentTimeMillis() / 1000
                currentTimerValue = it
                _timerState.value = TimerState(
                    value = it,
                    isRunning = currentTimerState == CurrentTimerState.Running
                )
            }.launchIn(scope)
        timerJob.start()
        return true
    }

    override fun stop() {
        timerJob.cancel()
        sharedPrefs.isTimerRunning = false
        currentTimerState = CurrentTimerState.Stopped
        _timerState.value = TimerState(
            value = currentTimerValue,
            isRunning = false
        )
    }

    override fun startTimer(period: Long) {
        start(period)
    }

    override fun stopTimer() {
        stop()
    }

    private fun initTimer(period: Long) = flow {
        while (currentTimerState == CurrentTimerState.Running) {
            emit(currentTimerValue + 1)
            delay(period)
        }
    }

    private fun getLastTimerState(): TimerState {
        val lastTimerValue = sharedPrefs.lastTimerValue
        val lastTimerTime = sharedPrefs.lastTimerTickTime
        var elapsedSeconds: Long = 0
        if (lastTimerTime != 0L) {
            elapsedSeconds = getElapsedSeconds(lastTimerTime)
        }
        return TimerState(value = lastTimerValue + elapsedSeconds, isRunning = sharedPrefs.isTimerRunning)
    }

    override fun shouldResumeTimerOnStart(): Boolean {
        return sharedPrefs.isTimerRunning && currentTimerState == CurrentTimerState.Initial
    }

    private fun getElapsedSeconds(timeInPast: Long): Long {
        val elapsed = System.currentTimeMillis() / 1000 - timeInPast
        return elapsed
    }
}

sealed class CurrentTimerState {
    object Initial : CurrentTimerState()
    object Running : CurrentTimerState()
    object Stopped : CurrentTimerState()
}