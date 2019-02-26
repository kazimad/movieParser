package com.kazimad.movieparser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.Profile
import com.kazimad.movieparser.MainInterface
import com.kazimad.movieparser.R
import com.kazimad.movieparser.utils.Constants.Companion.PROFILE_PARAM

class MainFragment : Fragment(), MainInterface {

    companion object {
        fun newInstance(profile: Profile): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle(1)
            bundle.putParcelable(PROFILE_PARAM, profile)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_main, container, false)
        return fragmentView
    }


}