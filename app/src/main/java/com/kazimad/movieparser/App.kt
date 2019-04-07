package com.kazimad.movieparser

import android.app.Application
import com.kazimad.movieparser.dagger.component.DaggerMainComponent
import com.kazimad.movieparser.dagger.component.MainComponent
import com.kazimad.movieparser.dagger.module.AppModule
import com.kazimad.movieparser.dagger.module.ContextModule
import com.kazimad.movieparser.dagger.module.RoomModule
import com.kazimad.movieparser.utils.Logger


open class App : Application() {


    override fun onCreate() {
        super.onCreate()
        Logger.init()
        instance = this
        configDagger()
    }

    private fun configDagger() {
        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule())
            .roomModule(RoomModule())
            .contextModule(ContextModule(applicationContext))
            .build()
    }

    companion object {
        lateinit var mainComponent: MainComponent
        lateinit var instance: Application
    }
}