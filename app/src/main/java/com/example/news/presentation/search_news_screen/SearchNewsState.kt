package com.example.news.presentation.search_news_screen

import com.example.news.domain.model.Article

data class SearchNewsState(
    val articleItems: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
