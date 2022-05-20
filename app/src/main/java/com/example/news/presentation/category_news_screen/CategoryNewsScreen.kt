package com.example.news.presentation.category_news_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.news.presentation.components.ArticleItem
import com.example.news.util.Category
import com.example.news.util.Country
import com.example.news.util.Screen
import kotlinx.coroutines.launch

@Composable
fun CategoryNewsScreen(
    navController: NavController,
    viewModel: CategoryNewsViewModel = hiltViewModel()
) {
    val newsState = viewModel.newsState.value
    val scaffoldState = rememberScaffoldState()
    val countryList = Country.getAllCountriesName()
    val categoryList = Category.getCategoryList()

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "熱門新聞",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        modifier = Modifier
                            .clickable {
                                viewModel.onEvent(CategoryNewsEvent.CountrySpinnerOpen)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = Country.getCountry(newsState.countryAbbrev),
                            style = MaterialTheme.typography.h5
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription ="Spinner Country"
                        )
                        DropdownMenu(
                            expanded = newsState.isCountryExpanded,
                            onDismissRequest = {
                                viewModel.onEvent(CategoryNewsEvent.CountrySpinnerClose)
                            }
                        ) {
                            countryList.forEach { country ->
                                DropdownMenuItem(onClick = {
                                    viewModel.onEvent(CategoryNewsEvent.CountrySpinnerClose)
                                    viewModel.onEvent(
                                        CategoryNewsEvent.CountryAndCategory(
                                            Country.getCountryAbbrev(country),
                                            newsState.categoryEnglish
                                    ))
                                    scope.launch {
                                        listState.animateScrollToItem(0)
                                    }
                                }
                                ) {
                                    Text(
                                        text = country,
                                        style = MaterialTheme.typography.h5
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        modifier = Modifier
                            .clickable {
                                viewModel.onEvent(CategoryNewsEvent.CategorySpinnerOpen)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = Category.getCategory(newsState.categoryEnglish),
                            style = MaterialTheme.typography.h5
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription ="Spinner Category"
                        )
                        DropdownMenu(
                            expanded = newsState.isCategoryExpanded,
                            onDismissRequest = {
                                viewModel.onEvent(CategoryNewsEvent.CategorySpinnerClose)
                            }
                        ) {
                            categoryList.forEach { category ->
                                DropdownMenuItem(onClick = {
                                    viewModel.onEvent(CategoryNewsEvent.CategorySpinnerClose)
                                    viewModel.onEvent(
                                        CategoryNewsEvent.CountryAndCategory(
                                        newsState.countryAbbrev,
                                        Category.getCategoryEnglish(category)
                                    ))
                                    scope.launch {
                                        listState.animateScrollToItem(0)
                                    }
                                }
                                ) {
                                    Text(
                                        text = category,
                                        style = MaterialTheme.typography.h5
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(newsState.articleItems.size) { i ->
                        val article = newsState.articleItems[i]
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
                        if (i < newsState.articleItems.size - 1) {
                            Divider()
                        }
                    }
                    item { Spacer(modifier = Modifier.height(25.dp)) }
                }
            }
            if (newsState.error.isNotBlank()) {
                Text(
                    text = newsState.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (newsState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}