package com.example.news.presentation.search_news_screen

import androidx.compose.ui.focus.FocusState

sealed class SearchNewsEvent {
    data class EnteredQuery(val query: String): SearchNewsEvent()
    data class ChangeQueryFocus(val focusState: FocusState): SearchNewsEvent()
    data class SearchNews(val searchQuery: String): SearchNewsEvent()
    object SearchClear: SearchNewsEvent()
}