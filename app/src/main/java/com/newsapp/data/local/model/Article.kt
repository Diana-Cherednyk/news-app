package com.newsapp.data.local.model

import com.newsapp.data.local.model.Article.Companion.TABLE_NAME
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = TABLE_NAME)
data class Article(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var source: String? = null,
    var url: String? = null,
    var imageUrl: String? = null
) {
    companion object {
        const val TABLE_NAME = "NewsArticle"
    }
}