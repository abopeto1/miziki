package com.levagency.miziki

import android.app.Application
import timber.log.Timber

class MizikiApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}