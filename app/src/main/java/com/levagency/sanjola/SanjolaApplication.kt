package com.levagency.sanjola

import android.app.Application
import timber.log.Timber

class SanjolaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}