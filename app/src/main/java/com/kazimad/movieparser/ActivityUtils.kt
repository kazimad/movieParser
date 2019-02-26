package com.kazimad.movieparser

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object ActivityUtils {

    fun addFragmentToActivity(activity: FragmentActivity, fragment: Fragment) {
        ActivityUtils.addFragmentToActivity(activity, fragment, false)
    }

    fun addFragmentToActivity(activity: FragmentActivity, fragment: Fragment, addToBackStack: Boolean) {
        ActivityUtils.addFragmentToActivity(activity, fragment, addToBackStack, R.id.fragmentContainer)
    }

    fun addFragmentToActivity(
        activity: FragmentActivity,
        fragment: Fragment,
        addToBackStack: Boolean,
        containerId: Int
    ) {
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commitAllowingStateLoss()
    }
}