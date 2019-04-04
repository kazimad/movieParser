package com.kazimad.movieparser.persistance

import androidx.lifecycle.MutableLiveData
import com.kazimad.movieparser.models.FavoriteData
import com.kazimad.movieparser.models.MovieData
import com.kazimad.movieparser.models.SectionedMovieItem
import com.kazimad.movieparser.persistance.data_sources.FavoriteDbDataSource
import com.kazimad.movieparser.persistance.data_sources.MovieDbDataSource
import kotlinx.coroutines.*
import javax.inject.Inject

class DbRepository @Inject constructor(favoriteDataSource: FavoriteDbDataSource, movieDbDataSource: MovieDbDataSource) {

    var favoriteDataSource = favoriteDataSource
    var movieDbDataSource = movieDbDataSource

    private var favoritesArray: MutableList<FavoriteData> = mutableListOf()
    private val favoriteIdsValues: MutableList<Int> = mutableListOf()

    fun getAllFavorites(favoriteLiveData: MutableLiveData<List<SectionedMovieItem>?>): Job {
        return GlobalScope.launch {
            withContext(Dispatchers.Main) {
                favoritesArray = favoriteDataSource.getAllFavorites() as MutableList<FavoriteData>
                favoritesArray.forEach { favoriteIdsValues.add(it.id) }
                val listPrepared = sortAndFilterValues(allMoviesArray)
                favoriteLiveData.value = markFavorite(listPrepared)
            }
        }
    }

    fun saveFavorites() {
        if (favoriteIdsValues.isNotEmpty()) {
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    favoriteDataSource.deleteAllFavorites()
                    favoritesArray.clear()
                    favoriteIdsValues.forEach { favoritesArray.add(FavoriteData(it)) }
                    favoriteDataSource.insertAllFavoriteDatas(favoritesArray)
                }
            }
        }
    }

    private fun markFavorite(listSorted: List<SectionedMovieItem>): List<SectionedMovieItem> {
        listSorted.forEach {
            it.value?.isFavorite = favoriteIdsValues.contains(it.value?.id)
        }
        return listSorted
    }
}