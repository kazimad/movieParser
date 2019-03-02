package com.kazimad.movieparser.ui.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kazimad.movieparser.MainInterface
import com.kazimad.movieparser.R
import com.kazimad.movieparser.adapters.MovieAdapter
import com.kazimad.movieparser.models.response.MovieData
import com.kazimad.movieparser.ui.main.MainFragmentViewModel
import com.kazimad.movieparser.utils.Logger

class FavoriteFragment : Fragment(), MainInterface {
    private lateinit var activityContext: MainInterface
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = (context as MainInterface)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_main, container, false)

        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        viewModel.moviesLiveData.observe(this, Observer { onMoviesObserved(it) })
        viewModel.errorLiveData.observe(this, Observer { onError(it) })

        recyclerView = fragmentView.findViewById(R.id.recyclerView)

        return fragmentView
    }

    private fun onMoviesObserved(result: List<MovieData>?) {
        result?.let {
            val adapter = MovieAdapter(result, recyclerView.context)
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter
            adapter.setVieModel(viewModel)

        }
        Logger.log("onMoviesObserved ${result?.size}")
    }

    private fun onError(error: Throwable) {
        Logger.log("onError ${error.message}")
    }
}