package com.kazimad.movieparser.repo

import androidx.lifecycle.MutableLiveData
import com.kazimad.movieparser.App
import com.kazimad.movieparser.entities.FavoriteEntity
import com.kazimad.movieparser.entities.MovieEntity
import com.kazimad.movieparser.entities.SectionedMovieItem
import com.kazimad.movieparser.entities.response.TopResponse
import com.kazimad.movieparser.enums.ListTypes
import com.kazimad.movieparser.sources.persistance.data_sources.FavoriteDbDataSource
import com.kazimad.movieparser.sources.persistance.data_sources.MovieDbDataSource
import com.kazimad.movieparser.sources.remote.ApiSource
import com.kazimad.movieparser.utils.Constants
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    var favoriteDataSource: FavoriteDbDataSource,
    var movieDbDataSource: MovieDbDataSource,
    var apiSource: ApiSource
) {

    private var favoritesList: MutableList<FavoriteEntity> = mutableListOf()
    var favoriteIdsList: MutableList<Int> = mutableListOf()
    private var allMoviesList: MutableList<MovieEntity> = mutableListOf()


    fun pickFavoritesFromMovieData(): MutableList<MovieEntity> {
        val result: MutableList<MovieEntity> = mutableListOf()
        allMoviesList.forEach {
            if (favoriteIdsList.contains(it.id)) {
                result.add(it)
            }
        }
        return result
    }


    fun showAllFavorite(moviesLiveData: MutableLiveData<List<SectionedMovieItem>?>) {
        moviesLiveData.value = markFavorite(sortAndFilterValues(allMoviesList))
    }

    fun getAllMovies(
        errorLiveData: MutableLiveData<Throwable>,
        moviesLiveData: MutableLiveData<List<SectionedMovieItem>?>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val request =
                apiSource.getList(
                    Constants.API_KEY,
                    Constants.API_SORT_BY,
                    getCurrentDateAndFormat(),
                    getFutureDateAndFormat()
                )
            try {
                val response = request.await()
                movieDbDataSource.deleteAll()
                movieDbDataSource.insertAll((response.body() as TopResponse).results)

            } catch (e: Throwable) {
                errorLiveData.value = e
            } finally {
                allMoviesList = ArrayList(movieDbDataSource.findAll())
                val listPrepared = sortAndFilterValues(allMoviesList)
                withContext(Dispatchers.Main) {
                    moviesLiveData.value = markFavorite(listPrepared)
                }
            }
        }
    }

    fun sortAndFilterValuesLiveData(
        unpreparedList: List<MovieEntity>,
        favoriteLiveData: MutableLiveData<List<SectionedMovieItem>?>
    ) {
        favoriteLiveData.value = sortAndFilterValues(unpreparedList)
    }

    fun saveFavorites() {
        if (favoriteIdsList.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                favoriteDataSource.deleteAllFavorites()
                favoritesList.clear()
                favoriteIdsList.forEach { favoritesList.add(FavoriteEntity(it)) }
                favoriteDataSource.insertAllFavoriteDatas(favoritesList)
            }
        }
    }

    private fun markFavorite(listSorted: List<SectionedMovieItem>): List<SectionedMovieItem> {
        listSorted.forEach {
            it.value?.isFavorite = favoriteIdsList.contains(it.value?.id)
        }
        return listSorted
    }

    private fun getAllFavorites(favoriteLiveData: MutableLiveData<List<SectionedMovieItem>?>): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            favoritesList = favoriteDataSource.getAllFavorites() as MutableList<FavoriteEntity>
            favoritesList.forEach { favoriteIdsList.add(it.id) }
            val listPrepared = sortAndFilterValues(allMoviesList)
            withContext(Dispatchers.Main) {
                favoriteLiveData.value = markFavorite(listPrepared)
            }
        }
    }

    private fun sortAndFilterValues(unpreparedList: List<MovieEntity>): List<SectionedMovieItem> {
        val technical: MutableList<SectionedMovieItem> = mutableListOf()
        val sortedListByMonth = unpreparedList.sortedWith(compareBy { it.releaseDate })
        val fullFormat = Constants.fullDateFormat
        val shortFormat = Constants.shortFormat
        var currentMonth: Int = -1

        // separate by month
        sortedListByMonth.forEach {
            run {
                val releaseDate = it.releaseDate
                val dateFull = fullFormat.parse(releaseDate)
                val dateShort = shortFormat.format(dateFull)
                val cal = Calendar.getInstance()!!
                cal.time = dateFull
                val month = cal.get(Calendar.MONTH)

                if (month != currentMonth) {
                    technical.add(SectionedMovieItem(ListTypes.HEADER, null, dateShort))
                    technical.add(SectionedMovieItem(ListTypes.REGULAR, it, null))
                    currentMonth = month
                } else {
                    technical.add(SectionedMovieItem(ListTypes.REGULAR, it, null))
                }
            }
        }

        //separate values in each collection by dates
        val rawHashMap = HashMap<String?, ArrayList<SectionedMovieItem>?>()
        var array: ArrayList<SectionedMovieItem>? = null
        var lastKey: String? = null
        technical.forEach {
            run {
                if (it.type == ListTypes.HEADER) {
                    if (lastKey != null) {
                        rawHashMap[lastKey] = array
                    }
                    lastKey = it.headerText
                    array = ArrayList()
                } else {
                    array?.add(it)
                }
            }
        }
        rawHashMap[lastKey] = array

        //sort each value list by popularity
        val sortedHashMap = HashMap<String?, List<SectionedMovieItem>?>()
        rawHashMap.forEach { (key, value) ->
            run {
                val sortedList = value?.sortedWith(compareBy { it.value?.popularity })
                sortedHashMap[key] = sortedList
            }
        }

        // compose sortered values and headers
        val sortedFilteredResult = ArrayList<SectionedMovieItem>()
        sortedHashMap.forEach { (key, value) ->
            run {
                if (value != null) {
                    sortedFilteredResult.add(
                        SectionedMovieItem(
                            ListTypes.HEADER,
                            null,
                            key
                        )
                    )
                    sortedFilteredResult.addAll(ArrayList(value))
                }
            }
        }
        return sortedFilteredResult
    }

    fun workWithLocalFavoriteData(movieEntity: MovieEntity, insert: Boolean) {
        if (insert) {
            favoriteIdsList.add(movieEntity.id)
        } else {
           favoriteIdsList.remove(movieEntity.id)
        }
    }

    private fun getCurrentDateAndFormat(): String {
        val cal = Calendar.getInstance()!!
        val dt = cal.time
        return Constants.fullDateFormat.format(dt)
    }

    private fun getFutureDateAndFormat(): String {
        val cal = Calendar.getInstance()!!
        cal.add(Calendar.MONTH, 3)
        val dt = cal.time
        return Constants.fullDateFormat.format(dt)
    }

}