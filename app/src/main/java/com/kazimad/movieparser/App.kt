package com.kazimad.movieparser

import android.app.Application
import com.kazimad.movieparser.dagger.component.MainComponent
import com.kazimad.movieparser.dagger.module.ApiModule
import com.kazimad.movieparser.utils.Logger

class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var mainComponent: MainComponent
    }


    override fun onCreate() {
        super.onCreate()
        mainComponent = DaggerMainComponent.builder().apiModule(ApiModule()).build()
        instance = this
        Logger.init()
    }

}