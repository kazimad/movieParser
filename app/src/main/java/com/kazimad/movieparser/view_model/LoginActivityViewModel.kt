package com.kazimad.movieparser.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.facebook.AccessToken

class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

    var loginCheckerLiveData = MutableLiveData<Boolean>()

    fun checkIsUserLoginIn() {
        val accessToken = AccessToken.getCurrentAccessToken()
        loginCheckerLiveData.value = accessToken != null && !accessToken.isExpired
    }
}