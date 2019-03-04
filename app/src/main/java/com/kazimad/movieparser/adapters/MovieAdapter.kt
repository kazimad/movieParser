package com.kazimad.movieparser.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kazimad.movieparser.R
import com.kazimad.movieparser.dagger.enums.MoviewItemClickVariant
import com.kazimad.movieparser.enums.ClickVariants
import com.kazimad.movieparser.enums.ListTypes
import com.kazimad.movieparser.interfaces.CustomClickListener
import com.kazimad.movieparser.remote.ApiProvider
import com.kazimad.movieparser.ui.main.MainFragmentViewModel
import com.kazimad.movieparser.utils.Logger
import com.kazimad.movieparser.utils.glide.Glider


class MovieAdapter(private val items: List<SectionedMovieItem>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mainFragmentViewModel: MainFragmentViewModel? = null
    var customClickListener: CustomClickListener? = null
    private val viewTypeHeader = 1
    private val viewTypeItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            viewTypeHeader -> return ViewHolderHeader(
                LayoutInflater.from(context).inflate(
                    R.layout.item_section_header,
                    parent,
                    false
                )
            )
            else -> {
                return ViewHolderItem(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_movie,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        Logger.log("getItemViewType holder.itemViewType is ${holder.itemViewType}")

        when (holder.itemViewType) {
            viewTypeHeader -> {
                val viewHolderHeader = holder as ViewHolderHeader
                val currentItem = items[position]
                viewHolderHeader.header.text = currentItem.headerText
            }
            else -> {
                val viewHolder = holder as ViewHolderItem
                val currentItem = items[position].value
                currentItem?.let {
                    Glider.downloadOrShowErrorSimple(
                        ApiProvider.baseImageUrl + currentItem.posterPath,
                        viewHolder.avatar
                    )
                    viewHolder.headerText.text = currentItem.originalTitle
                    viewHolder.descriptionText.text = currentItem.overview
                    viewHolder.ratingText.text = currentItem.popularity.toString()
                    viewHolder.favoriteText.text = if (!currentItem.isFavorite) context.getString(R.string.item_add_to_favorite) else context.getString(
                        R.string.item_remove_from_favorite
                    )
                    viewHolder.favoriteContainer.setOnClickListener {
                        mainFragmentViewModel?.let {
                            Logger.log("currentItem.isFavorite is ${currentItem.isFavorite}")
                            viewHolder.favoriteText.text = if (currentItem.isFavorite) context.getString(R.string.item_add_to_favorite) else context.getString(
                                        R.string.item_remove_from_favorite
                                    )
                            if (currentItem.isFavorite) {
                                mainFragmentViewModel!!.onMovieButtonClick(
                                    MoviewItemClickVariant.REMOVE_FAVORITE,
                                    currentItem
                                )
                            } else {
                                mainFragmentViewModel!!.onMovieButtonClick(
                                    MoviewItemClickVariant.ADD_FAVORITE,
                                    currentItem
                                )
                            }
                            currentItem.isFavorite = !currentItem.isFavorite
                            notifyDataSetChanged();
                        }
                    }
                    viewHolder.shareContainer.setOnClickListener {
                        customClickListener?.onCustomClick(ClickVariants.SHARE_CLICK, currentItem)
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
//        Logger.log("getItemViewType items[position].type is ${items[position].type}")
        return when (items[position].type) {
            ListTypes.HEADER -> viewTypeHeader
            else -> {
                viewTypeItem
            }
        }
    }

    fun setVieModel(mainFragmentViewModel: MainFragmentViewModel) {
        this.mainFragmentViewModel = mainFragmentViewModel
    }

    fun setCustomClick(customClickListener: CustomClickListener) {
        this.customClickListener = customClickListener
    }
}

class ViewHolderItem(view: View) : RecyclerView.ViewHolder(view) {
    var avatar = view.findViewById<ImageView>(R.id.avatar)
    var headerText = view.findViewById<TextView>(R.id.headerTV)
    var descriptionText = view.findViewById<TextView>(R.id.descriptionTV)
    var ratingText = view.findViewById<TextView>(R.id.ratingTV)
    var favoriteContainer = view.findViewById<LinearLayout>(R.id.favoriteContainer)
    var favoriteText = view.findViewById<TextView>(R.id.favoriteText)
    var shareContainer = view.findViewById<LinearLayout>(R.id.shareContainer)
}

class ViewHolderHeader(view: View) : RecyclerView.ViewHolder(view) {
    var header = view.findViewById<TextView>(R.id.headerTV)
}