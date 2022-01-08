package com.example.news.data.repository

import com.example.news.data.local.ArticleDao
import com.example.news.data.remote.NewsApi
import com.example.news.domain.model.Article
import com.example.news.domain.repository.ArticleRepository
import com.example.news.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class ArticleRepositoryImpl(
    private val api: NewsApi,
    private val dao: ArticleDao
): ArticleRepository {
    override fun getTopArticles(country: String): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading<List<Article>>())

        try {
            val news = api.getBreakingNews(countryCode = country)
            val articles = news.articles.map { it.toArticle() }
            emit(Resource.Success(articles))
        } catch (e: IOException) {
            emit(Resource.Error<List<Article>>(
                message = "Couldn't reach server, check your internet connection."
            ))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Article>>(
                message = "Oops, somethings went wrong!"
            ))
        }
    }

    override fun searchArticle(searchQuery: String): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())

        try {
            val news = api.searchForNews(searchQuery = searchQuery)
            val articles = news.articles.map { it.toArticle() }
            emit(Resource.Success(articles))
        } catch (e: IOException) {
            emit(Resource.Error<List<Article>>(
                message = "Couldn't reach server, check your internet connection."
            ))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Article>>(
                message = "Oops, somethings went wrong!"
            ))
        }
    }

    override fun getCategoryArticles(
        country: String,
        category: String
    ): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading<List<Article>>())

        try {
            val news = api.getCategoryNews(countryCode = country, category = category)
            val articles = news.articles.map { it.toArticle() }
            emit(Resource.Success(articles))
        } catch (e: IOException) {
            emit(Resource.Error<List<Article>>(
                message = "Couldn't reach server, check your internet connection."
            ))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Article>>(
                message = "Oops, somethings went wrong!"
            ))
        }
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return dao.getSavedArticles()
    }

    override suspend fun deleteArticle(article: Article) {
       return dao.deleteArticle(article)
    }

    override suspend fun insertArticle(article: Article) {
        return dao.insertArticle(article)
    }
}