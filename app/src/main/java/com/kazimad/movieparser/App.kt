package com.kazimad.movieparser

import android.app.Application
import com.kazimad.movieparser.utils.Logger


class App : Application() {


    override fun onCreate() {
        super.onCreate()
        Logger.init()
        instance = this
    }

    companion object {
        lateinit var instance: Application
    }
}