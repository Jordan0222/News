package com.example.news.util

object Category {
    private val categoryList = listOf(
        "經濟", "娛樂", "健康",
        "自然", "運動", "科技"
    )

    private val categoryEnglishList = listOf(
        "business", "entertainment", "health",
        "science", "sports", "technology"
    )

    fun getCategoryList() : List<String> {
        return categoryList
    }

    fun getCategoryEnglish(category: String) : String {
        val index = categoryList.indexOf(category)
        return categoryEnglishList[index]
    }

    fun getCategory(categoryEnglish: String) : String {
        val index = categoryEnglishList.indexOf(categoryEnglish)
        return categoryList[index]
    }
}