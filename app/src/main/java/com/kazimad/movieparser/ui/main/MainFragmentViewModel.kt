package com.kazimad.movieparser.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kazimad.movieparser.adapters.SectionedMovieItem
import com.kazimad.movieparser.dagger.component.DaggerMainComponent
import com.kazimad.movieparser.dagger.enums.MoviewItemClickVariant
import com.kazimad.movieparser.dagger.module.ApiModule
import com.kazimad.movieparser.dagger.module.ContextModule
import com.kazimad.movieparser.dagger.module.RoomModule
import com.kazimad.movieparser.enums.ListTypes
import com.kazimad.movieparser.models.FavoriteData
import com.kazimad.movieparser.models.MovieData
import com.kazimad.movieparser.models.response.TopResponse
import com.kazimad.movieparser.persistance.DbDataSource
import com.kazimad.movieparser.persistance.FavoriteDataSource
import com.kazimad.movieparser.remote.ApiSource
import com.kazimad.movieparser.utils.Constants
import com.kazimad.movieparser.utils.Constants.Companion.fullDateFormat
import com.kazimad.movieparser.utils.Constants.Companion.shortFormat
import com.kazimad.movieparser.utils.Logger
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var dbSource: DbDataSource
    @Inject
    lateinit var favoriteDataSource: FavoriteDataSource
    @Inject
    lateinit var apiSource: ApiSource


    //    private var compositeDisposable = CompositeDisposable()
    var moviesLiveData: MutableLiveData<List<SectionedMovieItem>?> = MutableLiveData()
    var favoriteLiveData: MutableLiveData<List<SectionedMovieItem>?> = MutableLiveData()
    var errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    private var favoritesArray: MutableList<FavoriteData> = mutableListOf()
    private var allMoviesArray: MutableList<MovieData> = mutableListOf()
    private val favoriteIdsValues: MutableList<Int> = mutableListOf()

    init {
        DaggerMainComponent.builder()
            .contextModule(ContextModule(application.applicationContext))
            .apiModule(ApiModule())
            .roomModule(RoomModule())
            .build()
            .injectToMainFragmentViewModel(this)
    }

    private fun getAllFavorites(): Job {
        return GlobalScope.launch {
            withContext(Dispatchers.Main) {
                favoritesArray = favoriteDataSource.getAllFavorites() as MutableList<FavoriteData>
                favoritesArray.forEach { favoriteIdsValues.add(it.id) }
                val listPrepared = sortAndFilterValues(allMoviesArray)
                favoriteLiveData.value = markFavorite(listPrepared)
            }
        }
    }

    fun showFavorites() {
        favoriteLiveData.value = sortAndFilterValues(pickFavoritesFromMoviewDatas())
    }

    private fun pickFavoritesFromMoviewDatas(): MutableList<MovieData> {
        val result: MutableList<MovieData> = mutableListOf()

        allMoviesArray.forEach {
            if (favoriteIdsValues.contains(it.id)) {
                result.add(it)
            }
        }
        return result
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

    fun getAllMovies() {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                val request =
                    apiSource.apiInterface?.getList(
                        Constants.API_KEY,
                        Constants.API_SORT_BY,
                        getCurrentDateAndFormat(),
                        getFutureDateAndFormat()
                    )
                try {
                    val response = request?.await()

                    dbSource.deleteAll()
                    dbSource.insertAll((response?.body() as TopResponse).results)

                } catch (e: HttpException) {
                    Logger.log("e2 is ${e.message()}, e.code is ${e.code()}")
                } catch (e: Throwable) {
                    Logger.log("e1 is ${e.message}")
                } finally {
                    allMoviesArray = ArrayList(dbSource.findAll())

                    val jobGetFavorite = getAllFavorites()
                    jobGetFavorite.join()

                    val listPrepared = sortAndFilterValues(allMoviesArray)
                    moviesLiveData.value = markFavorite(listPrepared)
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

    fun onMovieButtonClick(clickVariant: MoviewItemClickVariant, movieData: MovieData) {
        when (clickVariant) {
            MoviewItemClickVariant.ADD_FAVORITE -> {
                workWithLocalFavoriteDatas(movieData, true)
            }
            MoviewItemClickVariant.REMOVE_FAVORITE -> {
                workWithLocalFavoriteDatas(movieData, false)
            }
        }
    }


    private fun workWithLocalFavoriteDatas(movieData: MovieData, insert: Boolean) {
        if (insert) {
            favoriteIdsValues.add(movieData.id)
        } else {
            favoriteIdsValues.remove(movieData.id)
        }
    }

    private fun getCurrentDateAndFormat(): String {
        val cal = Calendar.getInstance()!!
        val dt = cal.time
        return fullDateFormat.format(dt)
    }

    private fun getFutureDateAndFormat(): String {
        val cal = Calendar.getInstance()!!
        cal.add(Calendar.MONTH, 3)
        val dt = cal.time
        return fullDateFormat.format(dt)
    }

    private fun sortAndFilterValues(unpreparedList: List<MovieData>): List<SectionedMovieItem> {
        val result: MutableList<SectionedMovieItem> = mutableListOf()
        val sortedListByMonth = unpreparedList.sortedWith(compareBy { it.releaseDate })
        val fullFormat = fullDateFormat
        val shortFormat = shortFormat
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
                    result.add(SectionedMovieItem(ListTypes.HEADER, null, dateShort))
                    result.add(SectionedMovieItem(ListTypes.REGULAR, it, null))
                    currentMonth = month
                } else {
                    result.add(SectionedMovieItem(ListTypes.REGULAR, it, null))
                }
            }
        }

        //separate values in each collection by dates
        val rawHashMap = HashMap<String?, ArrayList<SectionedMovieItem>?>()
        var array: ArrayList<SectionedMovieItem>? = null
        var lastKey: String? = null
        result.forEach {
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
                sortedFilteredResult.add(SectionedMovieItem(ListTypes.HEADER, null, key))
                sortedFilteredResult.addAll(ArrayList(value))
            }
        }

        return sortedFilteredResult
    }
}
