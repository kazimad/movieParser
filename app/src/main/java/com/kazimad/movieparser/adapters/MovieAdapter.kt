package com.kazimad.movieparser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kazimad.movieparser.R
import com.kazimad.movieparser.dagger.enums.MoviewItemClickVariant
import com.kazimad.movieparser.models.response.MovieData
import com.kazimad.movieparser.remote.ApiProvider
import com.kazimad.movieparser.ui.main.MainFragmentViewModel
import com.kazimad.movieparser.utils.glide.Glider

class MovieAdapter(val items: List<MovieData>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    var mainFragmentViewModel: MainFragmentViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        Glider.downloadOrShowErrorSimple(ApiProvider.baseImageUrl + currentItem.posterPath, holder.avatar)
        holder.headerText.text = currentItem.originalTitle
        holder.descriptionText.text = currentItem.overview
        holder.ratingText.text = currentItem.voteAverage.toString()
        holder.favoriteContainer.setOnClickListener {
            mainFragmentViewModel?.let {
                mainFragmentViewModel!!.onMovieButtonClick(MoviewItemClickVariant.ADD_FAVORITE, currentItem)
            }
        }
        holder.shareContainer.setOnClickListener {
            mainFragmentViewModel?.let {
                mainFragmentViewModel!!.onMovieButtonClick(MoviewItemClickVariant.SHARE, currentItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setVieModel(mainFragmentViewModel: MainFragmentViewModel) {
        this.mainFragmentViewModel = mainFragmentViewModel
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var avatar = view.findViewById<ImageView>(R.id.avatar)!!
    var headerText = view.findViewById<TextView>(R.id.headerTV)!!
    var descriptionText = view.findViewById<TextView>(R.id.descriptionTV)!!
    var ratingText = view.findViewById<TextView>(R.id.ratingTV)!!
    var favoriteContainer = view.findViewById<LinearLayout>(R.id.favoriteContainer)!!
    var shareContainer = view.findViewById<LinearLayout>(R.id.shareContainer)!!
}