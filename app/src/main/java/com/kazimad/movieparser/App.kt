package com.kazimad.movieparser

import android.app.Application
import com.kazimad.movieparser.dagger.component.DaggerMainComponent
import com.kazimad.movieparser.dagger.component.MainComponent
import com.kazimad.movieparser.dagger.module.ApiModule
import com.kazimad.movieparser.dagger.module.AppModule
import com.kazimad.movieparser.dagger.module.RoomModule
import com.kazimad.movieparser.utils.Logger


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
        Logger.init()
    }

    private fun initDagger() {
        DaggerMainComponent.builder()
            .appModule(AppModule(this))
            .apiModule(ApiModule())
            .roomModule(RoomModule(this))
            .build()
            .inject(this)
    }
}