package com.example.news.presentation.breaking_news_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.repository.ArticleRepository
import com.example.news.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val repository: ArticleRepository
): ViewModel() {

    private val _newsState = mutableStateOf(BreakingNewsState())
    val newsState: State<BreakingNewsState> = _newsState

    private var getArticlesJob: Job? = null

    init {
        getNews("tw")
    }

    fun onEvent(event: BreakingNewsEvent) {
        when (event) {
            is BreakingNewsEvent.CountryAbbrev -> {
                if (newsState.value.countryAbbrev == event.countryAbbrev) {
                    return
                }
                getNews(event.countryAbbrev)
            }
            is BreakingNewsEvent.SpinnerClose -> {
                _newsState.value = newsState.value.copy(
                    isExpanded = false
                )
            }
            is BreakingNewsEvent.SpinnerOpen -> {
                _newsState.value = newsState.value.copy(
                    isExpanded = true
                )
            }
        }
    }

    private fun getNews(countryAbbrev: String) {
        getArticlesJob?.cancel()
        getArticlesJob = viewModelScope.launch {
            repository.getTopArticles(countryAbbrev)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _newsState.value = newsState.value.copy(
                                articleItems = result.data ?: emptyList(),
                                countryAbbrev = countryAbbrev,
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _newsState.value = newsState.value.copy(
                                isLoading = false,
                                error = result.message ?: "Unknown Error"
                            )
                        }
                        is Resource.Loading -> {
                            _newsState.value = newsState.value.copy(
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }
}