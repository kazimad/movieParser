package com.kazimad.movieparser.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kazimad.movieparser.dagger.component.DaggerMainComponent
import com.kazimad.movieparser.dagger.enums.MoviewItemClickVariant
import com.kazimad.movieparser.dagger.module.ApiModule
import com.kazimad.movieparser.dagger.module.ContextModule
import com.kazimad.movieparser.dagger.module.RoomModule
import com.kazimad.movieparser.models.response.MovieData
import com.kazimad.movieparser.models.response.TopResponse
import com.kazimad.movieparser.persistance.DbDataSource
import com.kazimad.movieparser.remote.ApiSource
import com.kazimad.movieparser.utils.Constants
import com.kazimad.movieparser.utils.Logger
//import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var dbSource: DbDataSource
    @Inject
    lateinit var apiSource: ApiSource


//    private var compositeDisposable = CompositeDisposable()
    var moviesLiveData: MutableLiveData<List<MovieData>?> = MutableLiveData()
    var errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    init {
        DaggerMainComponent.builder()
            .contextModule(ContextModule(application.applicationContext))
            .apiModule(ApiModule())
            .roomModule(RoomModule())
            .build()
            .injectToMainFragmentViewModel(this)
    }


    fun callListResults() {
        apiSource.apiInterface
        GlobalScope.launch {
            withContext(Dispatchers.Main) {


                val request =
                    apiSource.apiInterface?.getList(Constants.API_KEY, Constants.API_SORT_BY, getCurrentDateAndFormat())
                try {
                    val response = request?.await()

                    dbSource.deleteAll()
                    dbSource.insertAll((response?.body() as TopResponse).results)
                } catch (e: HttpException) {
                    Logger.log("e is ${e.message()}, e.code is ${e.code()}")
                } catch (e: Throwable) {
                    Logger.log("e is ${e.message}")
                } finally {
                    moviesLiveData.value = dbSource.findAll()
                }
            }
        }

//        apiProvider.getListWithData(getCurrentDateAndFormat())?.subscribe({ result ->
//            val listMovies = result as ArrayList<MovieData>
//            Logger.log("MainFragmentViewModel callListResults ${listMovies[0]}")
//            saveToDb(listMovies[0])
//
//            moviesLiveData.value = result
//        })
//        { error ->
//            errorLiveData.value = Throwable(error.localizedMessage)
//            error.printStackTrace()
//        }?.let { compositeDisposable.add(it) }

    }

    fun disposeAll() {
//        compositeDisposable.clear()
//        compositeDisposable.dispose()
    }

    fun onMovieButtonClick(clickVariant: MoviewItemClickVariant, movieData: MovieData) {
        Logger.log("MainFragmentViewModel onMovieButtonClick $clickVariant, movieData ${movieData.id}")

    }

    private fun getCurrentDateAndFormat(): String {
        val milliseconds = System.currentTimeMillis()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val resultDate = Date(milliseconds)
        return sdf.format(resultDate)
    }

}
