package com.example.news.presentation.search_news_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    testTag: String = "",
    onFocusChange: (FocusState) -> Unit,
    onSearch: (String) -> Unit,
    onClear: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .background(Color(0xFFD5DBDB), RoundedCornerShape(percent = 50))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Search,
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Search News"
                    )
                    Box(modifier = Modifier.fillMaxWidth(0.8f)) {
                        if (isHintVisible) {
                            Text(text = hint, style = textStyle, color = Color.DarkGray)
                        }
                        innerTextField()
                    }
                    IconButton(
                        onClick = { onClear() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Search Clear"
                        )
                    }
                }
            },
            modifier = Modifier
                .testTag(testTag)
                .fillMaxWidth()
                .padding(top = 12.dp, start = 10.dp, end = 10.dp, bottom = 8.dp)
                .onFocusChanged {
                    onFocusChange(it)
                },
            keyboardActions = KeyboardActions { onSearch(text) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
        )
    }
}