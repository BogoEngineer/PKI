package com.veskekatke.healthformula.application

import android.app.Application
import timber.log.Timber

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init(){
        initTimber()
    }

    private fun initTimber(){
        Timber.plant(Timber.DebugTree())
    }
}