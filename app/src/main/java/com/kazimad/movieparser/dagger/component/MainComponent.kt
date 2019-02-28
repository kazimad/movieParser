package com.kazimad.movieparser.dagger.component

import android.app.Application
import com.kazimad.movieparser.dagger.module.ApiModule
import com.kazimad.movieparser.dagger.module.RoomModule
import com.kazimad.movieparser.persistance.AppDatabase
import com.kazimad.movieparser.persistance.MovieDao
import com.kazimad.movieparser.persistance.ProductRepository
import com.kazimad.movieparser.remote.ApiInterface
import com.kazimad.movieparser.remote.ApiProvider
import com.kazimad.movieparser.remote.UsersRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, RoomModule::class])
interface MainComponent {

    fun productDao(): MovieDao
    fun demoDatabase(): AppDatabase
    fun productRepository(): ProductRepository
//    fun application(): Application
    fun getApi(): ApiInterface
    fun getUserRepository(): UsersRepository
    fun getApiProvider(): ApiProvider
}