package com.kazimad.movieparser.remote

import androidx.lifecycle.MutableLiveData
import com.kazimad.movieparser.enums.ListTypes
import com.kazimad.movieparser.models.MovieData
import com.kazimad.movieparser.models.SectionedMovieItem
import com.kazimad.movieparser.models.response.TopResponse
import com.kazimad.movieparser.persistance.DbRepository
import com.kazimad.movieparser.persistance.data_sources.MovieDbDataSource
import com.kazimad.movieparser.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject

class ApiRepository @Inject constructor(apiSource: ApiSource, dbRepository: DbRepository) {

    var apiSource: ApiSource = apiSource
    var dbRepository: DbRepository = dbRepository

    private var allMoviesArray: MutableList<MovieData> = mutableListOf()


    fun getAllMovies(
        errorLiveData: MutableLiveData<Throwable>,
        moviesLiveData: MutableLiveData<List<SectionedMovieItem>?>
    ) {
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

                    dbDataSource.deleteAll()
                    dbDataSource.insertAll((response?.body() as TopResponse).results)

                } catch (e: HttpException) {
                    errorLiveData.value = e
                } catch (e: Throwable) {
                    errorLiveData.value = e
                } finally {
                    allMoviesArray = ArrayList(dbDataSource.findAll())

                    val jobGetFavorite = dbRepository.getAllFavorites()
                    jobGetFavorite.join()

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