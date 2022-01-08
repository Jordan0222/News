package com.example.news.domain.use_cases.api

import com.example.news.domain.model.Article
import com.example.news.domain.repository.ArticleRepository
import com.example.news.util.Resource
import kotlinx.coroutines.flow.Flow

class GetCategoryArticles(
    private val repository: ArticleRepository
) {
    operator fun invoke(country: String, category: String): Flow<Resource<List<Article>>> {
        return repository.getCategoryArticles(country, category)
    }
}