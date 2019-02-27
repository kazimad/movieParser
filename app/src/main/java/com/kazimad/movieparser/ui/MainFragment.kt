package com.kazimad.movieparser.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.Profile
import com.kazimad.movieparser.MainInterface
import com.kazimad.movieparser.R
import com.kazimad.movieparser.models.response.MovieData
import com.kazimad.movieparser.utils.Constants.Companion.PROFILE_PARAM
import com.kazimad.movieparser.utils.Logger

class MainFragment : Fragment(), MainInterface {
    private lateinit var activityContext: MainInterface
    private lateinit var viewModel: MainFragmentViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = (context as MainInterface)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_main, container, false)
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        viewModel.moviesLiveData.observe(this, Observer { onMoviesObserved(it) })
        viewModel.errorLiveData.observe(this, Observer { onError(it) })

        viewModel.callListResults()
        return fragmentView
    }


    private fun onMoviesObserved(result: ArrayList<MovieData>?) {
        Logger.log("onMoviesObserved ${result?.size}")
    }

    private fun onError(error: Throwable) {
        Logger.log("onError ${error?.message}")

    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.disposeAll()
    }

    companion object {
        fun newInstance(profile: Profile): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle(1)
            bundle.putParcelable(PROFILE_PARAM, profile)
            fragment.arguments = bundle
            return fragment
        }
    }
}