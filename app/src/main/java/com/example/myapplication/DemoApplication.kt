package com.example.myapplication

import android.app.Application
import com.example.myapplication.local.AppPreference
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DemoApplication: Application() {

    companion object {
        var instance: DemoApplication? = null
            private set
            @Synchronized
            get
    }

    override fun onCreate() {
        super.onCreate()
        init(this)
    }

    private fun init(app: DemoApplication) {
        instance = app
        AppPreference.getInstance(app)
    }
}