package com.example.news.presentation.breaking_news_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.news.presentation.components.ArticleItem
import com.example.news.util.Country
import com.example.news.util.Screen

@Composable
fun BreakingNewsScreen(
    navController: NavController,
    viewModel: BreakingNewsViewModel = hiltViewModel()
) {

    val newsState = viewModel.newsState.value
    val scaffoldState = rememberScaffoldState()
    val countryList = Country.getAllCountriesName()

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
                    Text(
                        text = "每日新聞",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        modifier = Modifier
                            .clickable {
                                viewModel.onEvent(BreakingNewsEvent.SpinnerOpen)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = Country.getCountry(newsState.countryAbbrev),
                            style = MaterialTheme.typography.h5
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription ="Spinner"
                        )
                        DropdownMenu(
                            expanded = newsState.isExpanded,
                            onDismissRequest = {
                                viewModel.onEvent(BreakingNewsEvent.SpinnerClose)
                            }
                        ) {
                            countryList.forEach { country ->
                                DropdownMenuItem(onClick = {
                                    viewModel.onEvent(BreakingNewsEvent.SpinnerClose)
                                    viewModel.onEvent(BreakingNewsEvent.CountryAbbrev(Country.getCountryAbbrev(country)))
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
                }
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
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
                        .align(Center)
                )
            }
            if (newsState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Center))
            }
        }
    }
}