package com.kazimad.movieparser.models.response

import com.google.gson.annotations.SerializedName
import com.kazimad.movieparser.models.response.ChildData

data class ChildrenItem(@SerializedName("data")
                        val data: ChildData,
                        @SerializedName("kind")
                        val kind: String = "")