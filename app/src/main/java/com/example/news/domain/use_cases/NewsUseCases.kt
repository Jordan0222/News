package com.jordan.newsapp.domain.use_cases

import com.example.news.domain.use_cases.api.GetCategoryArticles
import com.example.news.domain.use_cases.api.GetTopArticles
import com.example.news.domain.use_cases.api.SearchArticles
import com.example.news.domain.use_cases.database.DeleteArticle
import com.example.news.domain.use_cases.database.GetSavedArticles
import com.example.news.domain.use_cases.database.SaveArticle

data class NewsUseCases(
    val getTopArticles: GetTopArticles,
    val getSavedArticles: GetSavedArticles,
    val deleteArticle: DeleteArticle,
    val saveArticle: SaveArticle,
    val searchArticles: SearchArticles,
    val getCategoryArticles: GetCategoryArticles
)