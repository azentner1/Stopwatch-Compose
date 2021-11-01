package com.hubstaff.challenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HsChallengeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: HsChallengeApplication
            private set
    }
}
