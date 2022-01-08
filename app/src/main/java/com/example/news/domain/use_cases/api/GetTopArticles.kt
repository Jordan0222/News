package com.example.news.domain.use_cases.api

import com.example.news.domain.model.Article
import com.example.news.domain.repository.ArticleRepository
import com.example.news.util.Resource
import kotlinx.coroutines.flow.Flow

class GetTopArticles(
    private val repository: ArticleRepository
) {
    operator fun invoke(country: String): Flow<Resource<List<Article>>> {
        return repository.getTopArticles(country)
    }
}