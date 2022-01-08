package com.example.news.domain.repository

import com.example.news.domain.model.Article
import com.example.news.util.Resource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getTopArticles(country: String): Flow<Resource<List<Article>>>

    fun getSavedArticles(): Flow<List<Article>>

    suspend fun deleteArticle(article: Article)

    suspend fun insertArticle(article: Article)

    fun searchArticle(searchQuery: String): Flow<Resource<List<Article>>>

    fun getCategoryArticles(
        country: String,
        category: String
    ): Flow<Resource<List<Article>>>
}