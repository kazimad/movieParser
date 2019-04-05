package com.kazimad.movieparser.repo

import androidx.lifecycle.MutableLiveData
import com.kazimad.movieparser.entities.FavoriteData
import com.kazimad.movieparser.entities.MovieData
import com.kazimad.movieparser.entities.SectionedMovieItem
import com.kazimad.movieparser.entities.response.TopResponse
import com.kazimad.movieparser.enums.ListTypes
import com.kazimad.movieparser.persistance.data_sources.FavoriteDbDataSource
import com.kazimad.movieparser.persistance.data_sources.MovieDbDataSource
import com.kazimad.movieparser.remote.ApiSource
import com.kazimad.movieparser.utils.Constants
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor(
    favoriteDataSource: FavoriteDbDataSource,
    movieDbDataSource: MovieDbDataSource,
    apiSource: ApiSource
) {

    var favoriteDataSource = favoriteDataSource
    var movieDbDataSource = movieDbDataSource
    var apiSource: ApiSource = apiSource

    private var favoritesArray: MutableList<FavoriteData> = mutableListOf()
    var favoriteIdsValues: MutableList<Int> = mutableListOf()
        private set
    private var allMoviesArray: MutableList<MovieData> = mutableListOf()


    fun pickFavoritesFromMovieDatas(): MutableList<MovieData> {
        val result: MutableList<MovieData> = mutableListOf()
        allMoviesArray.forEach {
            if (favoriteIdsValues.contains(it.id)) {
                result.add(it)
            }
        }
        return result
    }


    fun showAllFavorite(moviesLiveData: MutableLiveData<List<SectionedMovieItem>?>) {
        moviesLiveData.value = markFavorite(sortAndFilterValues(allMoviesArray))
    }

    fun getAllMovies(
        errorLiveData: MutableLiveData<Throwable>,
        moviesLiveData: MutableLiveData<List<SectionedMovieItem>?>,
        favoriteLiveData: MutableLiveData<List<SectionedMovieItem>?>
    ) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
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

                } catch (e: HttpException) {
                    errorLiveData.value = e
                } catch (e: Throwable) {
                    errorLiveData.value = e
                } finally {
                    allMoviesArray = ArrayList(movieDbDataSource.findAll())

//                    val jobGetFavorite = getAllFavorites(favoriteLiveData)
//                    jobGetFavorite.join()

                    val listPrepared = sortAndFilterValues(allMoviesArray)
                    moviesLiveData.value = markFavorite(listPrepared)
                }
            }
        }
    }

    fun sortAndFilterValuesLiveData(
        unpreparedList: List<MovieData>,
        favoriteLiveData: MutableLiveData<List<SectionedMovieItem>?>
    ) {
        favoriteLiveData.value = sortAndFilterValues(unpreparedList)
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

    private fun getAllFavorites(favoriteLiveData: MutableLiveData<List<SectionedMovieItem>?>): Job {
        return GlobalScope.launch {
            withContext(Dispatchers.Main) {
                favoritesArray = favoriteDataSource.getAllFavorites() as MutableList<FavoriteData>
                favoritesArray.forEach { favoriteIdsValues.add(it.id) }
                val listPrepared = sortAndFilterValues(allMoviesArray)
                favoriteLiveData.value = markFavorite(listPrepared)
            }
        }
    }

    private fun sortAndFilterValues(unpreparedList: List<MovieData>): List<SectionedMovieItem> {
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