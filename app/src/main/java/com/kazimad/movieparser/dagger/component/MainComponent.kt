package com.kazimad.movieparser.dagger.component

import com.kazimad.movieparser.dagger.module.AppModule
import com.kazimad.movieparser.dagger.module.ContextModule
import com.kazimad.movieparser.dagger.module.RoomModule
import com.kazimad.movieparser.persistance.DbRepository
import com.kazimad.movieparser.persistance.data_sources.FavoriteDbDataSource
import com.kazimad.movieparser.remote.ApiSource
import com.kazimad.movieparser.remote.ApiRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RoomModule::class, ContextModule::class])
interface MainComponent {

    fun getApi(): ApiSource
    fun getDbRepository(): DbRepository
    fun getApiRepository(): ApiRepository
}