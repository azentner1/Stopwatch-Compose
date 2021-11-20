package com.stopwatch.shared_prefs

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

class SharedPrefs(
    @ApplicationContext private val context: Context
) {
    val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(context.packageName + "." + SharedPrefs::class.java.simpleName,
            Context.MODE_PRIVATE)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    var isLoggedIn: Boolean by DelegatedPreferences(prefs, false)
    var lastTimerTickTime: Long by DelegatedPreferences(prefs, 0)
    var lastTimerValue: Long by DelegatedPreferences(prefs, 0)
    var isTimerRunning: Boolean by DelegatedPreferences(prefs, false)
}


@Module
@InstallIn(SingletonComponent::class)
object SharedPrefsProvider {

    @Singleton
    @Provides
    fun provideSharedPrefs(
        @ApplicationContext context: Context
    ): SharedPrefs = SharedPrefs(context)
}