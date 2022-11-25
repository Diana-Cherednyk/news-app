package com.newsapp.data.remote.model
import com.newsapp.domain.ArticlesResponse
import retrofit2.http.GET
interface ApiFactory {

    @GET("top-headlines?country=ua&apiKey=bc3c5392175a47509e3de5c96024e920")
    suspend fun getNewsArticles(): ArticlesResponse

    companion object {
        const val NEWS_API_URL = "https://newsapi.org/v2/"
    }
}
