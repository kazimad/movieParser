package com.kazimad.movieparser.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kazimad.movieparser.App
import com.kazimad.movieparser.dagger.enums.MovieItemClickVariant
import com.kazimad.movieparser.entities.MovieEntity
import com.kazimad.movieparser.entities.SectionedMovieItem


class MainFragmentViewModel : ViewModel() {

    var moviesLiveData: MutableLiveData<List<SectionedMovieItem>?> = MutableLiveData()
    var favoriteLiveData: MutableLiveData<List<SectionedMovieItem>?> = MutableLiveData()
    var errorLiveData: MutableLiveData<Throwable> = MutableLiveData()


    fun getAllMovies() {
        App.mainComponent.getRepository().getAllMovies(errorLiveData, moviesLiveData)
    }

    fun showFavorites() {
        App.mainComponent.getRepository().sortAndFilterValuesLiveData(
            App.mainComponent.getRepository().pickFavoritesFromMovieData(),
            favoriteLiveData
        )
    }

    fun saveFavorites() {
        App.mainComponent.getRepository().saveFavorites()
    }

    fun showMovieItems() {
        App.mainComponent.getRepository().showAllFavorite(moviesLiveData)
    }


    fun onMovieButtonClick(clickVariant: MovieItemClickVariant, movieEntity: MovieEntity) {
        when (clickVariant) {
            MovieItemClickVariant.ADD_FAVORITE -> {
                App.mainComponent.getRepository().workWithLocalFavoriteData(movieEntity, true)
            }
            MovieItemClickVariant.REMOVE_FAVORITE -> {
                App.mainComponent.getRepository().workWithLocalFavoriteData(movieEntity, false)
                showFavorites()
            }
        }
    }


}
