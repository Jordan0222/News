package com.example.news.presentation.category_news_screen

sealed class CategoryNewsEvent {
    data class CountryAndCategory(val countryAbbrev: String, val categoryEnglish: String) : CategoryNewsEvent()
    object CountrySpinnerClose : CategoryNewsEvent()
    object CountrySpinnerOpen : CategoryNewsEvent()
    object CategorySpinnerClose : CategoryNewsEvent()
    object CategorySpinnerOpen : CategoryNewsEvent()
}