package com.kazimad.movieparser.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kazimad.movieparser.App
import com.kazimad.movieparser.dagger.enums.MovieItemClickVariant
import com.kazimad.movieparser.models.MovieData
import com.kazimad.movieparser.models.SectionedMovieItem


class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var moviesLiveData: MutableLiveData<List<SectionedMovieItem>?> = MutableLiveData()
    var favoriteLiveData: MutableLiveData<List<SectionedMovieItem>?> = MutableLiveData()
    var errorLiveData: MutableLiveData<Throwable> = MutableLiveData()


    fun getAllMovies() {
        App.mainComponent.getApiRepository().getAllMovies(errorLiveData, moviesLiveData)
    }

    fun showFavorites() {
        App.mainComponent.getApiRepository()
            .sortAndFilterValuesLiveData(pickFavoritesFromMovieDatas(), favoriteLiveData)
    }

    fun showMovieItems() {
        val listPrepared = sortAndFilterValues(allMoviesArray)
        moviesLiveData.value = markFavorite(listPrepared)
    }

    private fun pickFavoritesFromMovieDatas(): MutableList<MovieData> {
        val result: MutableList<MovieData> = mutableListOf()
        allMoviesArray.forEach {
            if (favoriteIdsValues.contains(it.id)) {
                result.add(it)
            }
        }
        return result
    }



    fun onMovieButtonClick(clickVariant: MovieItemClickVariant, movieData: MovieData) {
        when (clickVariant) {
            MovieItemClickVariant.ADD_FAVORITE -> {
                workWithLocalFavoriteDatas(movieData, true)
            }
            MovieItemClickVariant.REMOVE_FAVORITE -> {
                workWithLocalFavoriteDatas(movieData, false)
            }
        }
    }

    private fun workWithLocalFavoriteDatas(movieData: MovieData, insert: Boolean) {
        if (insert) {
            favoriteIdsValues.add(movieData.id)
        } else {
            favoriteIdsValues.remove(movieData.id)
            showFavorites()
        }
    }


}
