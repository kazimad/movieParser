package com.kazimad.movieparser.models.response

import com.google.gson.annotations.SerializedName
import com.kazimad.movieparser.models.response.ChildrenItem

data class Data(@SerializedName("modhash")
                val modhash: String = "",
                @SerializedName("children")
                val children: List<ChildrenItem>?,
                @SerializedName("before")
                val before: Any? = null,
                @SerializedName("dist")
                val dist: Int = 0,
                @SerializedName("after")
                val after: String = "")