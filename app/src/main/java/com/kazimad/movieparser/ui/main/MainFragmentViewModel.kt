package com.kazimad.movieparser.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kazimad.movieparser.App
import com.kazimad.movieparser.dagger.enums.MovieItemClickVariant
import com.kazimad.movieparser.entities.MovieData
import com.kazimad.movieparser.entities.SectionedMovieItem


class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var moviesLiveData: MutableLiveData<List<SectionedMovieItem>?> = MutableLiveData()
    var favoriteLiveData: MutableLiveData<List<SectionedMovieItem>?> = MutableLiveData()
    var errorLiveData: MutableLiveData<Throwable> = MutableLiveData()


    fun getAllMovies() {
        App.mainComponent.getRepository().getAllMovies(errorLiveData, moviesLiveData, favoriteLiveData)
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


    fun onMovieButtonClick(clickVariant: MovieItemClickVariant, movieData: MovieData) {
        when (clickVariant) {
            MovieItemClickVariant.ADD_FAVORITE -> {
                workWithLocalFavoriteData(movieData, true)
            }
            MovieItemClickVariant.REMOVE_FAVORITE -> {
                workWithLocalFavoriteData(movieData, false)
            }
        }
    }

    private fun workWithLocalFavoriteData(movieData: MovieData, insert: Boolean) {
        if (insert) {
            App.mainComponent.getRepository().favoriteIdsValues.add(movieData.id)
        } else {
            App.mainComponent.getRepository().favoriteIdsValues.remove(movieData.id)
            showFavorites()
        }
    }
}
