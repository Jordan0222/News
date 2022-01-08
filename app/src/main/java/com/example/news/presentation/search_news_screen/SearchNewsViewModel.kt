package com.example.news.presentation.search_news_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.use_cases.NewsUseCases
import com.example.news.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    private val _searchQuery = mutableStateOf(
        TextFieldState(
        hint = "搜尋 ..."
    )
    )
    val searchQuery: State<TextFieldState> = _searchQuery

    private val _state = mutableStateOf(SearchNewsState())
    val state: State<SearchNewsState> = _state

    private var searchJob: Job? = null

    fun onEvent(event: SearchNewsEvent) {
        when (event) {
            is SearchNewsEvent.EnteredQuery -> {
                _searchQuery.value = searchQuery.value.copy(
                    text = event.query
                )
            }
            is SearchNewsEvent.ChangeQueryFocus -> {
                _searchQuery.value = searchQuery.value.copy(
                    isHintVisible = !event.focusState.isFocused && searchQuery.value.text.isBlank()
                )
            }
            is SearchNewsEvent.SearchClear -> {
                _searchQuery.value = searchQuery.value.copy(
                    text = ""
                )
            }
            is SearchNewsEvent.SearchNews -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    newsUseCases.searchArticles(searchQuery.value.text)
                        .onEach { result ->
                            when (result) {
                                is Resource.Success -> {
                                    _state.value = SearchNewsState(
                                        articleItems = result.data ?: emptyList(),
                                        isLoading = false
                                    )
                                }
                                is Resource.Error -> {
                                    _state.value = SearchNewsState(
                                        articleItems = result.data ?: emptyList(),
                                        isLoading = false,
                                        error = result.message ?: "Unknown error"
                                    )
                                }
                                is Resource.Loading -> {
                                    _state.value = SearchNewsState(
                                        articleItems = result.data ?: emptyList(),
                                        isLoading = true
                                    )
                                }
                            }
                        }.launchIn(this)
                }
            }
        }
    }
}