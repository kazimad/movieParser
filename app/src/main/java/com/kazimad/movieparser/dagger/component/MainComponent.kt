package com.kazimad.movieparser.dagger.component

import com.kazimad.movieparser.App
import com.kazimad.movieparser.adapters.MovieAdapter
import com.kazimad.movieparser.dagger.module.ApiModule
import com.kazimad.movieparser.dagger.module.ContextModule
import com.kazimad.movieparser.dagger.module.RoomModule
import com.kazimad.movieparser.ui.main.MainFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, RoomModule::class, ContextModule::class])
interface MainComponent {

    fun inject(app: App)
    fun injectToMainFragmentViewModel(mainFragmentViewModel: MainFragmentViewModel)
    fun injectToMovieAdapter(movieAdapter: MovieAdapter)
}