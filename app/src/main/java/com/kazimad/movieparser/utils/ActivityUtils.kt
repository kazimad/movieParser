package com.kazimad.movieparser.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.kazimad.movieparser.R

object ActivityUtils {

    fun addFragmentToActivity(activity: FragmentActivity, fragment: Fragment) {
        addFragmentToActivity(activity, fragment, false)
    }

    private fun addFragmentToActivity(activity: FragmentActivity, fragment: Fragment, addToBackStack: Boolean) {
        addFragmentToActivity(
            activity,
            fragment,
            addToBackStack,
            R.id.fragmentContainer
        )
    }

    private fun addFragmentToActivity(
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