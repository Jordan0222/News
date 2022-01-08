package com.example.news.di

import android.app.Application
import androidx.room.Room
import com.example.news.data.local.ArticleDatabase
import com.example.news.data.remote.NewsApi
import com.example.news.data.repository.ArticleRepositoryImpl
import com.example.news.domain.repository.ArticleRepository
import com.example.news.domain.use_cases.api.GetCategoryArticles
import com.example.news.domain.use_cases.api.GetTopArticles
import com.example.news.domain.use_cases.api.SearchArticles
import com.example.news.domain.use_cases.database.DeleteArticle
import com.example.news.domain.use_cases.database.GetSavedArticles
import com.example.news.domain.use_cases.database.SaveArticle
import com.example.news.util.Constants.Companion.BASE_URL
import com.jordan.newsapp.domain.use_cases.NewsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideArticleDatabase(app: Application): ArticleDatabase {
        return Room.databaseBuilder(
            app,
            ArticleDatabase::class.java,
            "article_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideArticleRepository(
        api: NewsApi,
        db: ArticleDatabase
    ): ArticleRepository {
        return ArticleRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(repository: ArticleRepository): NewsUseCases {
        return NewsUseCases(
            getTopArticles = GetTopArticles(repository),
            getSavedArticles = GetSavedArticles(repository),
            searchArticles = SearchArticles(repository),
            saveArticle = SaveArticle(repository),
            deleteArticle = DeleteArticle(repository),
            getCategoryArticles = GetCategoryArticles(repository)
        )
    }
}