package com.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newsapp.data.local.model.Article
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<Article>)

    @Query("DELETE FROM ${Article.TABLE_NAME}")
    suspend fun deleteAllPosts()

    @Query("SELECT * FROM ${Article.TABLE_NAME}")
    suspend fun getAllArticles(): List<Article>
}
