package com.netsoft.android.timer.repo

import com.netsoft.android.timer.data.TimerDataSource
import com.netsoft.android.timer.data.TimerState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimerRepository @Inject constructor(
    private val timerDataSource: TimerDataSource
) {
    fun startTimer(period: Long) {
        timerDataSource.startTimer(period)
    }

    fun stopTimer() {
        timerDataSource.stopTimer()
    }

    fun observeTimer(): Flow<TimerState> {
        return timerDataSource.timerState
    }

    fun shouldResumeTimerOnStart(): Boolean {
        return timerDataSource.shouldResumeTimerOnStart()
    }
}