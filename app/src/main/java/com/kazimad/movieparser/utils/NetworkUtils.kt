package com.kazimad.movieparser.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkUtils {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivity.activeNetworkInfo
            if (info != null) {
                if (info.detailedState == NetworkInfo.DetailedState.CONNECTED) {
                    return true
                }
            }
            return false
        }
    }
}