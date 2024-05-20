package com.appmilla.kmpjetpackdemo

import android.app.Application
import com.appmilla.kmpjetpackdemo.di.androidModule
import di.KoinInitializer

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KoinInitializer(applicationContext).init(androidModule)
    }
}