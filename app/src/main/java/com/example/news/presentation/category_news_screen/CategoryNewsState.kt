package com.example.news.presentation.category_news_screen

import com.example.news.domain.model.Article

data class CategoryNewsState(
    val articleItems: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val isCountryExpanded: Boolean = false,
    val isCategoryExpanded: Boolean = false,
    val countryAbbrev: String = "tw",
    val categoryEnglish: String = "business",
    val error: String = ""
)