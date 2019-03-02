package com.kazimad.movieparser.remote

import com.kazimad.movieparser.models.response.MovieData
import com.kazimad.movieparser.models.response.TopResponse
import com.kazimad.movieparser.utils.Constants
//import io.reactivex.Observable
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ApiSource @Inject constructor() {

    var apiInterface: ApiInterface? = null
        @Inject set

//    fun getListWithData(releaseDate: String): Observable<List<MovieData>>? {
//        return apiInterface!!.getList(Constants.API_KEY, Constants.API_SORT_BY, releaseDate)
//            .filter(ApiHelper.baseApiFilterPredicate())
//            .subscribeOn(Schedulers.io())
//            .flatMap { getListMoviesFromResponse(it.body()!!) }
//            .observeOn(AndroidSchedulers.mainThread())
//    }

//    private fun getListMoviesFromResponse(topResponse: TopResponse): Observable<List<MovieData>> {
//        return Observable.fromArray(topResponse.results)
//    }
}