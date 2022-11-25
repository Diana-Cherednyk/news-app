package com.newsapp.data.remote.model
import com.squareup.moshi.Json
import com.newsapp.data.remote.model.Article

data class NewsResult(
    @field:Json(name = "status") var status: String? = null,
    @field:Json(name = "totalResults") var totalResults: Int? = null,
    @field:Json(name = "articles") var articles: List<Article>? = null
)
