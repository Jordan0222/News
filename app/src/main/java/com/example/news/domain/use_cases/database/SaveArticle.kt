package com.example.news.domain.use_cases.database

import com.example.news.domain.model.Article
import com.example.news.domain.repository.ArticleRepository

class SaveArticle(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(article: Article) {
        return repository.insertArticle(article)
    }
}