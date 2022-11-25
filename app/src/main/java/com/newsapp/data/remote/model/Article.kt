package com.newsapp.data.remote.model
import com.squareup.moshi.Json
import com.newsapp.data.remote.model.Source

data class Article(
    @field:Json(name = "title") var title: String? = null,
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "url") var url: String? = null,
    @field:Json(name = "urlToImage") var urlToImage: String? = null,
    @field:Json(name = "source") var source: Source? = null
)
