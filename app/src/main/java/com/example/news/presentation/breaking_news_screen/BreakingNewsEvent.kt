package com.example.news.presentation.breaking_news_screen

sealed class BreakingNewsEvent {
    data class CountryAbbrev(val countryAbbrev: String): BreakingNewsEvent()
    object SpinnerClose: BreakingNewsEvent()
    object SpinnerOpen: BreakingNewsEvent()
}