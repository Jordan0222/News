package com.example.news.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.news.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertArticle(article: Article)

    @Query("SELECT * FROM articles")
    fun getSavedArticles(): Flow<List<Article>>

    @Delete
    fun deleteArticle(article: Article)
}