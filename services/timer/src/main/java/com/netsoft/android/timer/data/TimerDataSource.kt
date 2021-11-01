package com.netsoft.android.timer.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

interface TimerDataSource {
    val timerState: Flow<TimerState>
    fun startTimer(period: Long)
    fun stopTimer()
    fun shouldResumeTimerOnStart(): Boolean
}

@Module
@InstallIn(SingletonComponent::class)
abstract class TimerDataSourceProvider {

    @Singleton
    @Binds
    internal abstract fun getTimerDataSource(
        impl: TimerDataSourceImpl,
    ): TimerDataSource
}