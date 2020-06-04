package com.oxecoder.customview

import android.app.Application
import com.oxecoder.customview.BuildConfig
import timber.log.Timber

class CustomViewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}