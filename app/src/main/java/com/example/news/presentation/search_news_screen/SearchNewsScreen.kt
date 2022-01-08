package com.example.news.presentation.search_news_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.news.presentation.components.ArticleItem
import com.example.news.presentation.search_news_screen.components.SearchTextField
import com.example.news.util.Screen

@Composable
fun SearchNewsScreen(
    navController: NavController,
    viewModel: SearchNewsViewModel = hiltViewModel()
) {

    val queryState = viewModel.searchQuery.value
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SearchTextField(
                    text = queryState.text,
                    hint = queryState.hint,
                    onValueChange = {
                        viewModel.onEvent(SearchNewsEvent.EnteredQuery(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(SearchNewsEvent.ChangeQueryFocus(it))
                    },
                    onSearch = {
                        viewModel.onEvent(SearchNewsEvent.SearchNews(it))
                    },
                    onClear = {
                        viewModel.onEvent(SearchNewsEvent.SearchClear)
                    },
                    isHintVisible = queryState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.articleItems.size) { i ->
                        val article = state.articleItems[i]
                        if (i > 0) {
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        ArticleItem(
                            article = article,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(
                                        Screen.ArticleScreen.route +
                                                "?url=${article.url}"
                                    )
                                }
                        )
                        if (i < state.articleItems.size - 1) {
                            Divider()
                        }
                    }
                    item { Spacer(modifier = Modifier.height(25.dp)) }
                }
            }
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}