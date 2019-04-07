package com.kazimad.movieparser.dagger.component

import com.kazimad.movieparser.dagger.module.AppModule
import com.kazimad.movieparser.dagger.module.ContextModule
import com.kazimad.movieparser.dagger.module.RoomModuleTest
import com.kazimad.movieparser.repo.Repository
import com.kazimad.movieparser.sources.remote.ApiSource
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, RoomModuleTest::class, ContextModule::class])
interface TestComponent {
    fun getApi(): ApiSource
    fun getRepository(): Repository

}