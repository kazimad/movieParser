package com.kazimad.movieparser.models.inner_models

import com.kazimad.movieparser.models.response.ChildrenItem


class ChildItemWrapper(var flag: Int = TopListAdapter.REGULAR_FLAG, var childWrappedData: ChildrenItem? = null
)