package com.newsapp.data.local

import com.newsapp.NewsApp

class NewsDB(private val application: NewsApp) {

    fun getNewsDatabase() = NewsDatabase.getInstance(application)
}
