package com.kazimad.movieparser.dagger.component

import com.kazimad.movieparser.dagger.module.ApiModule
import com.kazimad.movieparser.remote.ApiInterface
import com.kazimad.movieparser.remote.ApiProvider
import com.kazimad.movieparser.remote.UsersRepository
import dagger.Component

@Component(modules = [ApiModule::class])
interface MainComponent {

    fun getApi(): ApiInterface
    fun getUserRepository(): UsersRepository
    fun getApiProvider(): ApiProvider
}