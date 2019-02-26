package com.kazimad.movieparser

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.init()
    }

}