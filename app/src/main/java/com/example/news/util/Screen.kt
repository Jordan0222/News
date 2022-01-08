package com.example.news.util

sealed class Screen(val route: String) {
    object BreakingNewsScreen: Screen("breaking_news_screen")
    object ArticleScreen: Screen("article_screen")
    object CategoryNewsScreen: Screen("category_news_screen")
    object SearchNewsScreen: Screen("search_news_screen")
}
