package com.example.news.presentation.article_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.model.Article
import com.example.news.domain.use_cases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(WebViewState())
    val state: State<WebViewState> = _state

    private var url: String = ""
    private var urlToImage: String = ""
    private var title: String = ""
    private var description: String = ""

    init {
        url = savedStateHandle.get<String>("url").toString()
        urlToImage = savedStateHandle.get<String>("urlToImage").toString()
        title = savedStateHandle.get<String>("title").toString()
        description = savedStateHandle.get<String>("description").toString()
    }

    fun onProgressChange(progress: Int) {
        _state.value = state.value.copy(
            webViewProgress = progress
        )
    }

    fun saveNews() {
        viewModelScope.launch {
            newsUseCases.saveArticle(
                Article(
                    url = url,
                    urlToImage = urlToImage,
                    title = title,
                    description = description
                )
            )
        }
    }
}