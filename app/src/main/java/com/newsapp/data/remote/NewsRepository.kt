package com.newsapp.data.remote

import com.newsapp.data.local.NewsDB
import com.newsapp.data.local.model.Article
import com.newsapp.data.remote.model.State
import com.newsapp.domain.ArticlesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

@ExperimentalCoroutinesApi
class NewsRepository(private val newsDbModule: NewsDB,
                     private val newsApiModule: NewsApi) {

    fun getNews() = flow {
        emit(State.Loading<List<Article>>())

        try {
            val apiResponse = fetchFromApi()

            val newsApiResponse = apiResponse.body()

            if (apiResponse.isSuccessful && newsApiResponse?.status.equals("ok", true)
                && !newsApiResponse?.articles.isNullOrEmpty()) {
                saveRemoteData(newsApiResponse?.articles!!)
            } else {
                emit(State.error<List<Article>>(apiResponse.message()))
            }
        } catch (e: Exception) {
            emit(State.error<List<Article>>("Network error! Can't get latest news articles"))
            Timber.e(e)
        }

        val localNewsArticles = fetchFromDatabase()
        if (!localNewsArticles.isNullOrEmpty()) {
            emit(State.success(localNewsArticles))
        } else {
            emit(State.error<List<Article>>("Can't get articles from the database"))
            Timber.e("Can't get articles from the database")
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun fetchFromApi(): ArticlesResponse =
        newsApiModule
            .getApiFactory()
            .getNewsArticles()

    private suspend fun fetchFromDatabase() = newsDbModule
        .getNewsDatabase()
        .getArticlesDao()
        .getAllArticles()

    private suspend fun saveRemoteData(remoteArticles: List<com.newsapp.data.remote.model.Article>) {
        newsDbModule
            .getNewsDatabase()
            .getArticlesDao()
            .deleteAllPosts()

        val articles = remoteArticles.map { apiArticle ->
            Article(
                title = apiArticle.title,
                description = apiArticle.description,
                url = apiArticle.url,
                imageUrl = apiArticle.urlToImage,
                source = apiArticle.source?.name
            )
        }

        newsDbModule
            .getNewsDatabase()
            .getArticlesDao()
            .insertArticles(articles)
    }
}
