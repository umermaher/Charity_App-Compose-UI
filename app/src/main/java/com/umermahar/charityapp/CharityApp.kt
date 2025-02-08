package com.umermahar.charityapp

import android.app.Application
import com.umermahar.charityapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CharityApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CharityApp)
            androidLogger()
            modules(appModule)
        }
    }

}