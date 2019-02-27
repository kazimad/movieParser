package com.kazimad.movieparser.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kazimad.movieparser.App.Companion.mainComponent
import com.kazimad.movieparser.models.response.MovieData
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*


class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var moviesLiveData: MutableLiveData<ArrayList<MovieData>?> = MutableLiveData()
    var errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    var compositeDisposable = CompositeDisposable()
//    var loadedChildrenItems = ArrayList<ChildrenItem>()
//    var lastPosition: Int = 0


    fun callListResults() {
        mainComponent.getUserRepository().getListWithData(getCurrentDateAndFormat())?.subscribe({ result ->
            moviesLiveData.value = result as ArrayList<MovieData>
        }) { error ->
            errorLiveData.value = Throwable(error.localizedMessage)
            error.printStackTrace()
        }?.let { compositeDisposable.add(it) }

    }

    fun disposeAll() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

    private fun getCurrentDateAndFormat(): String {
        val milliseconds = System.currentTimeMillis()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val resultDate = Date(milliseconds)
        return sdf.format(resultDate)
    }

}
