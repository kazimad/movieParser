package com.kazimad.movieparser.utils.glide

import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.kazimad.movieparser.R
import com.kazimad.movieparser.utils.Logger


class Glider {
    companion object {
        // There are other loading methods in the instance, find them by  GlideApp.with( regular
        fun downloadOrShowErrorSimple(url: String?, imageView: ImageView?) {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.ic_image_holder)
            requestOptions.error(R.drawable.ic_no_image)
            Logger.log("Glider url is $url")
            if (!TextUtils.isEmpty(url) && imageView != null) {
                Logger.log("Glider success")
                GlideApp.with(imageView.context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
            }
        }
    }
}