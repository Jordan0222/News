package com.example.news.presentation.category_news_screen

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
class CategoryNewsViewModel @Inject constructor(
    private val repository: ArticleRepository
): ViewModel(){

    private val _newsState = mutableStateOf(CategoryNewsState())
    val newsState: State<CategoryNewsState> = _newsState

    private var getCategoryArticlesJob: Job? = null

    init {
        getCategoryNews("tw", "business")
    }

    fun onEvent(event: CategoryNewsEvent) {
        when (event) {
            is CategoryNewsEvent.CountryAndCategory -> {
                if (newsState.value.countryAbbrev == event.countryAbbrev &&
                    newsState.value.categoryEnglish == event.categoryEnglish
                ) {
                    return
                }
                getCategoryNews(event.countryAbbrev, event.categoryEnglish)
            }
            is CategoryNewsEvent.CountrySpinnerClose -> {
                _newsState.value = newsState.value.copy(
                    isCountryExpanded = false
                )
            }
            is CategoryNewsEvent.CountrySpinnerOpen -> {
                _newsState.value = newsState.value.copy(
                    isCountryExpanded = true
                )
            }
            is CategoryNewsEvent.CategorySpinnerClose -> {
                _newsState.value = newsState.value.copy(
                    isCategoryExpanded = false
                )
            }
            is CategoryNewsEvent.CategorySpinnerOpen -> {
                _newsState.value = newsState.value.copy(
                    isCategoryExpanded = true
                )
            }
        }
    }

    private fun getCategoryNews(countryAbbrev: String, categoryEnglish: String) {
        getCategoryArticlesJob?.cancel()
        getCategoryArticlesJob = viewModelScope.launch {
            repository.getCategoryArticles(countryAbbrev, categoryEnglish)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _newsState.value = newsState.value.copy(
                                articleItems = result.data ?: emptyList(),
                                countryAbbrev = countryAbbrev,
                                categoryEnglish = categoryEnglish,
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
