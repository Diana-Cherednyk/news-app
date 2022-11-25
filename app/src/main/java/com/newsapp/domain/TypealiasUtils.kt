package com.newsapp.domain
import com.newsapp.data.remote.model.NewsResult
import com.newsapp.data.remote.model.State
import retrofit2.Response
typealias ArticlesResponse = Response<NewsResult>

typealias ArticleListState = State<List<com.newsapp.data.local.model.Article>>
