package com.kazimad.movieparser.models.response

import com.google.gson.annotations.SerializedName
import com.kazimad.movieparser.models.response.Data

data class TopResponse(@SerializedName("data")
                       val data: Data,
                       @SerializedName("kind")
                       val kind: String = "")