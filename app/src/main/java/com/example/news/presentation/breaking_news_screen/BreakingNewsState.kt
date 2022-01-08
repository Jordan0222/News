package com.example.news.presentation.breaking_news_screen

import com.example.news.domain.model.Article

data class BreakingNewsState(
    val articleItems: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val isExpanded: Boolean = false,
    val countryAbbrev: String = "tw",
    val error: String = ""
)