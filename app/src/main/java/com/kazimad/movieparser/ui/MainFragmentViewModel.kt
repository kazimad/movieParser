package com.kazimad.movieparser.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kazimad.movieparser.dagger.component.DaggerMainComponent
import com.kazimad.movieparser.dagger.module.ApiModule
import com.kazimad.movieparser.dagger.module.AppModule
import com.kazimad.movieparser.dagger.module.RoomModule
import com.kazimad.movieparser.models.response.MovieData
import com.kazimad.movieparser.persistance.DbRepository
import com.kazimad.movieparser.remote.ApiRepository
import com.kazimad.movieparser.utils.Logger
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var dbRepository: DbRepository
    @Inject
    lateinit var apiRepository: ApiRepository


    var moviesLiveData: MutableLiveData<ArrayList<MovieData>?> = MutableLiveData()
    var errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    var compositeDisposable = CompositeDisposable()

    init {
        DaggerMainComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModule())
            .roomModule(RoomModule(application))
            .build()
            .injectToMainFragmentViewModel(this)
    }

    fun callListResults() {
        apiRepository.getListWithData(getCurrentDateAndFormat())?.subscribe({ result ->
            val listMovies = result as ArrayList<MovieData>
            Logger.log("MainFragmentViewModel callListResults ${listMovies[0]}")
            saveToDb(listMovies[0])
            
            moviesLiveData.value = result
        })
        { error ->
            errorLiveData.value = Throwable(error.localizedMessage)
            error.printStackTrace()
        }?.let { compositeDisposable.add(it) }

    }

    fun disposeAll() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

    private fun saveToDb(movieData: MovieData) {

//        var runnable = Runnable {dbRepository.insert(movieData)  }
//        var thread = Thread(runnable)
//        thread.start()

//        Completable.fromRunnable { dbRepository.insert(movieData) }.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                object : Observer<Void> {
//                    override fun onSubscribe(d: Disposable) {
//                        Logger.log("MainFragmentViewModel onSubscribe")
//
//                    }
//
//                    override fun onNext(t: Void) {
//                        Logger.log("MainFragmentViewModel onNext")
//                    }
//
//                    override fun onError(e: Throwable) {
//                        Logger.log("MainFragmentViewModel e.message ${e.message}")
//                    }
//
//
//                    override fun onComplete() {
//                        Logger.log("MainFragmentViewModel callListResults findAll is ${dbRepository.findAll().value}")
//                    }
//                }
//            }
    }

    private fun getCurrentDateAndFormat(): String {
        val milliseconds = System.currentTimeMillis()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val resultDate = Date(milliseconds)
        return sdf.format(resultDate)
    }

}
