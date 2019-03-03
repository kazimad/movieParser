package com.kazimad.movieparser.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kazimad.movieparser.InterfaceActivity
import com.kazimad.movieparser.InterfaceFragment
import com.kazimad.movieparser.R
import com.kazimad.movieparser.adapters.MovieAdapter
import com.kazimad.movieparser.adapters.SectionedMovieItem
import com.kazimad.movieparser.enums.ClickVariants
import com.kazimad.movieparser.interfaces.CustomClickListener
import com.kazimad.movieparser.models.response.MovieData
import com.kazimad.movieparser.utils.Logger


class MainFragment : Fragment(), InterfaceFragment, CustomClickListener {


    private lateinit var activityContext: InterfaceActivity
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = (context as InterfaceActivity)
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        viewModel.moviesLiveData.observe(this, Observer { onMoviesObserved(it) })
        viewModel.errorLiveData.observe(this, Observer { onError(it) })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_main, container, false)

        recyclerView = fragmentView.findViewById(R.id.recyclerView)
        swipeContainer = fragmentView.findViewById<View>(R.id.swiperefresh) as SwipeRefreshLayout

        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllMovies()

        swipeContainer.setColorSchemeColors(resources.getColor(R.color.tomato_two))
        swipeContainer.setOnRefreshListener {
            viewModel.getAllMovies()
            swipeContainer.isRefreshing = false
        }
    }


    private fun onMoviesObserved(result: List<SectionedMovieItem>?) {
        result?.let {
            val adapter = MovieAdapter(result, recyclerView.context)
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter
            adapter.setVieModel(viewModel)
            adapter.setCustomClick(this)

        }
        Logger.log("onMoviesObserved ${result?.size}")
    }

    private fun onError(error: Throwable) {
        Logger.log("onError ${error.message}")
    }

    override fun onTabSelected() {
    }

    override fun onCustomClick(variants: ClickVariants, moviewData: MovieData) {
        if (variants == ClickVariants.SHARE_CLICK) {
            shareMovie(moviewData)
        }
    }

    private fun shareMovie(movieData: MovieData) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            movieData.toString()
        )
        sendIntent.type = "text/plain"
        if (context != null && context?.packageManager != null) {
            if (sendIntent.resolveActivity(context!!.packageManager!!) != null) {
                this.startActivity(sendIntent)
            } else {
                Toast.makeText(context, "No suitable app found!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}