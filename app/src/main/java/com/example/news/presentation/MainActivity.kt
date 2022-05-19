package com.example.news.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberNew
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.news.presentation.article_screen.ArticleScreen
import com.example.news.presentation.breaking_news_screen.BreakingNewsScreen
import com.example.news.presentation.category_news_screen.CategoryNewsScreen
import com.example.news.presentation.components.BottomNavItem
import com.example.news.presentation.components.BottomNavigationBar
import com.example.news.presentation.search_news_screen.SearchNewsScreen
import com.example.news.ui.theme.NewsTheme
import com.example.news.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsTheme {
                BottomBarNavigation()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomBarNavigation() {
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "熱門新聞",
                        route = Screen.BreakingNewsScreen.route,
                        icon = Icons.Default.FiberNew
                    ),
                    BottomNavItem(
                        name = "焦點新聞",
                        route = Screen.CategoryNewsScreen.route,
                        icon = Icons.Default.Language
                    ),
                    /*BottomNavItem(
                        name = "珍藏",
                        route = Screen.SaveNewsScreen.route,
                        icon = Icons.Default.Bookmark
                    ),*/
                    BottomNavItem(
                        name = "搜尋",
                        route = Screen.SearchNewsScreen.route,
                        icon = Icons.Default.Search
                    ),
                ),
                navController = navController,
                bottomBarState = bottomBarState
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.BreakingNewsScreen.route
        ) {
            composable(
                route = Screen.BreakingNewsScreen.route
            ) {
                bottomBarState.value = true
                BreakingNewsScreen(navController = navController)
            }

            composable(
                route = Screen.CategoryNewsScreen.route
            ) {
                bottomBarState.value = true
                CategoryNewsScreen(navController = navController)
            }

            composable(
                route = Screen.SearchNewsScreen.route
            ) {
                bottomBarState.value = true
                SearchNewsScreen(navController = navController)
            }

            composable(
                route = Screen.ArticleScreen.route + "?url={url}",
                arguments = listOf(
                    navArgument("url") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = true
                    }
                )
            ) {
                bottomBarState.value = false
                ArticleScreen()
            }
        }
    }
}
