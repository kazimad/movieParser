package com.kazimad.movieparser.dagger.component

import android.app.Application
import com.kazimad.movieparser.App
import com.kazimad.movieparser.dagger.module.ApiModule
import com.kazimad.movieparser.dagger.module.AppModule
import com.kazimad.movieparser.dagger.module.RoomModule
import com.kazimad.movieparser.persistance.AppDatabase
import com.kazimad.movieparser.persistance.DbRepository
import com.kazimad.movieparser.persistance.MovieDao
import com.kazimad.movieparser.remote.ApiInterface
import com.kazimad.movieparser.remote.ApiProvider
import com.kazimad.movieparser.remote.ApiRepository
import com.kazimad.movieparser.ui.MainFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, RoomModule::class, AppModule::class])
interface MainComponent {

    fun inject(app: App)
    fun injectToMainFragmentViewModel(mainFragmentViewModel: MainFragmentViewModel)

    fun productDao(): MovieDao
    fun demoDatabase(): AppDatabase
    fun productRepository(): DbRepository
    fun application(): Application
    fun getApi(): ApiInterface
    fun getUserRepository(): ApiRepository
    fun getApiProvider(): ApiProvider
}