package com.kazimad.movieparser.ui.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kazimad.movieparser.InterfaceActivity
import com.kazimad.movieparser.InterfaceFragment
import com.kazimad.movieparser.R
import com.kazimad.movieparser.adapters.MovieAdapter
import com.kazimad.movieparser.enums.ClickVariants
import com.kazimad.movieparser.interfaces.CustomClickListener
import com.kazimad.movieparser.models.MovieData
import com.kazimad.movieparser.ui.main.MainFragmentViewModel
import com.kazimad.movieparser.utils.Logger

class FavoriteFragment : Fragment(), InterfaceFragment, CustomClickListener {

    private lateinit var activityContext: InterfaceActivity
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private var movieResults: List<MovieData> = ArrayList<MovieData>()
    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.log("FavoriteFragment onAttach ")
        activityContext = (context as InterfaceActivity)

        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
//        viewModel.favoriteLiveData.observe(this, Observer { onFavoriteObserved(it) })
        viewModel.errorLiveData.observe(this, Observer { onError(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = fragmentView.findViewById(R.id.recyclerView)
        swipeContainer = fragmentView.findViewById<View>(R.id.swiperefresh) as SwipeRefreshLayout

        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getAllFavorites()
//        adapter = MovieAdapter(movieResults, recyclerView.context)
//        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)
//        recyclerView.adapter = adapter
//        adapter.setVieModel(viewModel)
//        adapter.setCustomClick(this)
//        swipeContainer.setColorSchemeColors(resources.getColor(R.color.tomato_two))
//        swipeContainer.setOnRefreshListener {
//            viewModel.getAllFavorites()
//            swipeContainer.isRefreshing = false
//        }
    }

    private fun onFavoriteObserved(result: List<MovieData>?) {
        result?.let {
            movieResults = result
            adapter.notifyDataSetChanged()
        }
        Logger.log("onFavoriteObserved ${result?.size}")
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
        this.startActivity(sendIntent)
    }


    override fun onTabSelected() {
        Logger.log("FavoriteFragment onTabSelected()")
//        if (!::viewModel.isInitialized) {
//            initViewModel()
//        }
        viewModel.getAllFavorites()
//        if (::viewModel.isInitialized) {
//            Logger.log("viewModel.isInitialized")
//        } else {
//            Logger.log("!!!!! viewModel.isInitialized")
//
//        }
    }

    private fun onError(error: Throwable) {
        Logger.log("onError ${error.message}")
    }


    override fun onResume() {
        super.onResume()
        Logger.log("FavoriteFragment onResume ")
    }

    override fun onPause() {
        super.onPause()
        Logger.log("FavoriteFragment onPause ")
    }

    override fun onStop() {
        super.onStop()
        Logger.log("FavoriteFragment onStop ")

    }

}