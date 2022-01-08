package com.example.news.presentation.article_screen

import android.annotation.SuppressLint
import android.webkit.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news.presentation.article_screen.components.CustomWebView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    url: String,
    viewModel: ArticleViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            CustomWebView(
                modifier = Modifier.fillMaxSize(),
                url = url,
                onProgressChange = { progress ->
                    viewModel.onProgressChange(progress)
                },
                initSettings = { webSettings ->
                    webSettings.apply {
                        //支持 js 交互
                        javaScriptEnabled = true
                        //將圖片調整到適合 webView 的大小
                        useWideViewPort = true
                        // 縮放至屏幕的大小
                        loadWithOverviewMode = true
                        // 縮放操作
                        setSupportZoom(true)
                        builtInZoomControls = true
                        displayZoomControls = true
                        // 是否支持通過 JS 打開新窗口
                        javaScriptCanOpenWindowsAutomatically = true
                        // 不加載缓存内容
                        cacheMode = WebSettings.LOAD_NO_CACHE
                    }
                }
            )
        }
        if (state.webViewProgress != 100) {
            LinearProgressIndicator(
                progress = state.webViewProgress * 1.0F / 100F,
                modifier = Modifier
                    .width(150.dp)
                    .height(15.dp)
                    .align(Alignment.Center),
                color = Color.Green
            )
        }
    }
}
