package com.chs.shared

import android.app.Application
import di.initKoin
import org.koin.android.ext.koin.androidContext

class YourSplashApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@YourSplashApplication)
        }
    }
}