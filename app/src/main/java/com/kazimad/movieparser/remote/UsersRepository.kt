package com.kazimad.movieparser.remote

import com.kazimad.movieparser.App
import com.kazimad.movieparser.remote.ApiProvider.Companion.baseUrl
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject



class UsersRepository @Inject constructor() {

    fun getListWithData(after: String?, limit: Int): Observable<Data>? {
        return App.mainComponent.getApiProvider().create(baseUrl).getList(after, limit)
                .filter(ApiHelper.baseApiFilterPredicate())
                .subscribeOn(Schedulers.io())
                .flatMap { getDataFromResponse(it.body()!!) }
                .observeOn(AndroidSchedulers.mainThread())

    }

    private fun getDataFromResponse(topResponse: TopResponse): Observable<Data> {
        return Observable.fromArray(topResponse.data)
    }
}