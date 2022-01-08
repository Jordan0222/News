package com.example.news.domain.use_cases.database

import com.example.news.domain.model.Article
import com.example.news.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetSavedArticles(
    private val repository: ArticleRepository
) {

    operator fun invoke(): Flow<List<Article>> {
        return repository.getSavedArticles()
    }
}