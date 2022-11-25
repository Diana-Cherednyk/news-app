package com.newsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.newsapp.data.local.model.Article

@Database(
    entities = [Article::class],
    version = NewsDatabase.DB_VERSION
)
abstract class NewsDatabase : RoomDatabase() {


    abstract fun getArticlesDao(): ArticlesDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "news_database"

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
