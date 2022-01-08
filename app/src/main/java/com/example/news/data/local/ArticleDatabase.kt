package com.example.news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.domain.model.Article

@Database(
    entities = [Article::class],
    version = 1
)
abstract class ArticleDatabase: RoomDatabase() {

    abstract val dao: ArticleDao
}