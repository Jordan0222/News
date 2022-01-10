package com.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.news.data.local.ArticleDao
import com.example.news.data.local.ArticleDatabase
import com.example.news.domain.model.Article
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ArticleDaoTest {

    private lateinit var database: ArticleDatabase
    private lateinit var dao: ArticleDao

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao
    }

    @Test
    fun insertArticle() = runBlockingTest {
        withContext(Dispatchers.Main) {
            val article = Article(
                "My name is Jordan",
                "Introduce",
                "https://jordan.com",
                "https://jordan.image.com",
                id = 1
            )
            dao.insertArticle(article)

            val articles = dao.getSavedArticles().first()

            assertThat(articles).contains(article)
        }
    }

    @Test
    fun deleteArticle() = runBlockingTest {
        withContext(Dispatchers.Main) {
            val article = Article(
                "My name is Jordan",
                "Introduce",
                "https://jordan.com",
                "https://jordan.image.com",
                id = 1
            )
            dao.insertArticle(article)
            dao.deleteArticle(article)

            val articles = dao.getSavedArticles().first()

            assertThat(articles).doesNotContain(article)
        }
    }

    @After
    fun tearDown() {
        database.close()

        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}