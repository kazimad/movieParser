package com.kazimad.movieparser.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kazimad.movieparser.interfaces.InterfaceFragment
import com.kazimad.movieparser.R
import com.kazimad.movieparser.interfaces.CustomClickListener
import com.kazimad.movieparser.view.BaseMovieFragment
import com.kazimad.movieparser.view.main.MainActivity
import com.kazimad.movieparser.view_model.MainFragmentViewModel

class FavoriteFragment : BaseMovieFragment(), InterfaceFragment, CustomClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_main, container, false)

        viewModel = ViewModelProviders.of(activityContext as MainActivity).get(MainFragmentViewModel::class.java)
        viewModel.favoriteLiveData.observe(this, Observer { onMoviesObserved(it) })
        viewModel.errorLiveData.observe(this, Observer { onError(it) })

        recyclerView = fragmentView.findViewById(R.id.recyclerView)
        swipeContainer = fragmentView.findViewById<View>(R.id.swiperefresh) as SwipeRefreshLayout
        loadingContainer = fragmentView.findViewById<View>(R.id.loadingContainer) as ConstraintLayout
        noResultsText = fragmentView.findViewById(R.id.noResultsTV)

        return fragmentView
    }


    override fun onTabSelected() {
        loadingContainer.visibility = View.VISIBLE
        viewModel.showFavorites()
    }

}