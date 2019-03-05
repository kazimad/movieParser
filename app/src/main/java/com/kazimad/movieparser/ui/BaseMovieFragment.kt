package com.kazimad.movieparser.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kazimad.movieparser.App
import com.kazimad.movieparser.InterfaceActivity
import com.kazimad.movieparser.R
import com.kazimad.movieparser.adapters.MovieAdapter
import com.kazimad.movieparser.adapters.SectionedMovieItem
import com.kazimad.movieparser.enums.ClickVariants
import com.kazimad.movieparser.interfaces.CustomClickListener
import com.kazimad.movieparser.models.MovieData
import com.kazimad.movieparser.models.error.ResponseException
import com.kazimad.movieparser.ui.main.MainFragmentViewModel
import com.kazimad.movieparser.utils.Logger

abstract class BaseMovieFragment : Fragment(), CustomClickListener {

    protected lateinit var activityContext: InterfaceActivity
    protected lateinit var viewModel: MainFragmentViewModel
    protected lateinit var loadingContainer: ConstraintLayout
    protected lateinit var swipeContainer: SwipeRefreshLayout
    protected lateinit var adapter: MovieAdapter
    protected lateinit var noResultsText: TextView
    protected lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = (context as InterfaceActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeContainer.setColorSchemeColors(ContextCompat.getColor(swipeContainer.context, R.color.tomato_two))

        loadingContainer.visibility = View.VISIBLE
        adapter = MovieAdapter()
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        adapter.setVieModel(viewModel)
        adapter.setCustomClick(this)

        swipeContainer.setOnRefreshListener {
            viewModel.showFavorites()
            swipeContainer.isRefreshing = false
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
                Toast.makeText(context, App.instance.getString(R.string.error_no_suitable_app), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    protected fun onMoviesObserved(result: List<SectionedMovieItem>?) {
        loadingContainer.visibility = View.GONE
        adapter.setItems(result)
        adapter.notifyDataSetChanged()

        if (result == null || result.isEmpty()) {
            noResultsText.visibility = View.VISIBLE
        } else {
            noResultsText.visibility = View.GONE
        }

        Logger.log("onMoviesObserved ${result?.size}")
    }

    protected fun onError(error: Throwable) {
        val errorMassage = this.getString(R.string.error_text)
        if (error is ResponseException) {
            errorMassage + error.code + " " + error.errorMessage
        }
        Toast.makeText(App.instance, errorMassage, Toast.LENGTH_LONG).show()
        loadingContainer.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveFavorites()
    }

    override fun onCustomClick(variants: ClickVariants, moviewData: MovieData) {
        if (variants == ClickVariants.SHARE_CLICK) {
            shareMovie(moviewData)
        }
    }
}