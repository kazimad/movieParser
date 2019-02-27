package com.kazimad.movieparser.utils.glide

import android.text.TextUtils
import android.widget.ImageView
import com.kazimad.movieparser.R


class Glider {
    companion object {
        // There are other loading methods in the app, find them by  GlideApp.with( regular
        fun downloadOrShowErrorSimple(url: String?, imageView: ImageView?) {
            try {
                if (!TextUtils.isEmpty(url) && imageView != null) {
                    GlideApp.with(imageView.context)
                        .load(url)
                        .into(imageView)
                } else {
                    imageView?.let {
                        GlideApp.with(imageView.context)
                            .load(R.drawable.shape_no_image)
                            .into(imageView)
                    }
                }
            } catch (e: IllegalStateException) {
                imageView?.let {
                    GlideApp.with(imageView.context)
                        .load(R.drawable.shape_no_image)
                        .into(imageView)
                }
                e.printStackTrace()
            }
        }
    }
}