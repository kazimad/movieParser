package com.kazimad.movieparser.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.facebook.AccessToken
import com.facebook.Profile


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    var profileLiveData = MutableLiveData<Profile>()
    var loginCheckerLiveData = MutableLiveData<Boolean>()

    fun checkIsUserLoginIn() {
        val accessToken = AccessToken.getCurrentAccessToken()
        loginCheckerLiveData.value = accessToken != null && !accessToken.isExpired
    }

    fun getCurrentProfile() {
        profileLiveData.value = Profile.getCurrentProfile()
    }
}
