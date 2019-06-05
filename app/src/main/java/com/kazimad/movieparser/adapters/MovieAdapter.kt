package com.kazimad.movieparser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kazimad.movieparser.R
import com.kazimad.movieparser.dagger.enums.MovieItemClickVariant
import com.kazimad.movieparser.entities.SectionedMovieItem
import com.kazimad.movieparser.enums.ClickVariants
import com.kazimad.movieparser.enums.ListTypes
import com.kazimad.movieparser.interfaces.CustomClickListener
import com.kazimad.movieparser.sources.remote.ApiSource
import com.kazimad.movieparser.utils.glide.Glider
import com.kazimad.movieparser.view_model.MainFragmentViewModel

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movieItems: MutableList<SectionedMovieItem>? = mutableListOf()
    private var viewModel: MainFragmentViewModel? = null
    private var customClickListener: CustomClickListener? = null

    private val viewTypeHeader = 1
    private val viewTypeItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            viewTypeHeader -> return ViewHolderHeader(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_section_header,
                    parent,
                    false
                )
            )
            else -> {
                return ViewHolderItem(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_movie,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        movieItems?.let {
            when (holder.itemViewType) {
                viewTypeHeader -> {
                    val viewHolderHeader = holder as ViewHolderHeader
                    val currentItem = movieItems!![position]
                    viewHolderHeader.header.text = currentItem.headerText
                }
                else -> {
                    val viewHolder = holder as ViewHolderItem
                    val currentItem = movieItems!![position].value
                    currentItem?.let {
                        Glider.downloadOrShowErrorSimple(
                            ApiSource.baseImageUrl + currentItem.posterPath,
                            viewHolder.avatar
                        )
                        viewHolder.apply {
                            headerText.text = currentItem.originalTitle
                            descriptionText.text = currentItem.overview
                            ratingText.text = currentItem.popularity.toString()
                            favoriteText.text =
                                if (!currentItem.isFavorite) favoriteText.context.getString(R.string.item_add_to_favorite)
                                else favoriteText.context.getString(R.string.item_remove_from_favorite)
                            favoriteContainer.setOnClickListener {
                                viewModel?.let {
                                    favoriteText.text =
                                        if (currentItem.isFavorite) favoriteContainer.context.getString(R.string.item_add_to_favorite)
                                        else favoriteContainer.context.getString(R.string.item_remove_from_favorite)
                                    if (currentItem.isFavorite) viewModel!!.onMovieButtonClick(
                                        MovieItemClickVariant.REMOVE_FAVORITE,
                                        currentItem
                                    )
                                    else viewModel!!.onMovieButtonClick(MovieItemClickVariant.ADD_FAVORITE, currentItem)
                                    currentItem.isFavorite = !currentItem.isFavorite
                                    notifyDataSetChanged()
                                }
                            }
                            shareContainer.setOnClickListener {
                                customClickListener?.onCustomClick(ClickVariants.SHARE_CLICK, currentItem)
                            }
                        }
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return if (movieItems != null) movieItems!!.size else -1
    }

    override fun getItemViewType(position: Int): Int {
        return if (movieItems != null) {
            when (movieItems!![position].type) {
                ListTypes.HEADER -> viewTypeHeader
                else -> {
                    viewTypeItem
                }
            }
        } else {
            -1
        }
    }

    fun setVieModel(mainFragmentViewModel: MainFragmentViewModel) {
        this.viewModel = mainFragmentViewModel
    }

    fun setCustomClick(customClickListener: CustomClickListener) {
        this.customClickListener = customClickListener
    }

    fun setItems(items: List<SectionedMovieItem>?) {
        movieItems = items as MutableList<SectionedMovieItem>
    }
}

class ViewHolderItem(view: View) : RecyclerView.ViewHolder(view) {
    var avatar = view.findViewById<ImageView>(R.id.avatar)!!
    var headerText = view.findViewById<TextView>(R.id.headerTV)!!
    var descriptionText = view.findViewById<TextView>(R.id.descriptionTV)!!
    var ratingText = view.findViewById<TextView>(R.id.ratingTV)!!
    var favoriteContainer = view.findViewById<LinearLayout>(R.id.favoriteContainer)!!
    var favoriteText = view.findViewById<TextView>(R.id.favoriteText)!!
    var shareContainer = view.findViewById<LinearLayout>(R.id.shareContainer)!!
}

class ViewHolderHeader(view: View) : RecyclerView.ViewHolder(view) {
    var header = view.findViewById<TextView>(R.id.headerTV)!!
}