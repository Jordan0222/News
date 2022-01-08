package com.example.news.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles"
)
data class Article(
    val description: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    @PrimaryKey val id: Int? = null
)