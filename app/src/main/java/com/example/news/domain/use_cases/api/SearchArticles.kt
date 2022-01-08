package com.example.news.domain.use_cases.api

import com.example.news.domain.model.Article
import com.example.news.domain.repository.ArticleRepository
import com.example.news.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchArticles(
    private val repository: ArticleRepository
) {

    operator fun invoke(searchQuery: String): Flow<Resource<List<Article>>> {
        if (searchQuery.isBlank()) {
            return flow {  }
        }
        return repository.searchArticle(searchQuery)
    }
}