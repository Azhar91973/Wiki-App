package com.example.wikiapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.wikiapp.api.CommonsApi
import com.example.wikiapp.api.WikiApi
import com.example.wikiapp.data.SharedPrefs
import com.example.wikiapp.db.AppDatabase
import com.example.wikiapp.db.dao.CategoryDao
import com.example.wikiapp.db.dao.CategoryRemoteKeyDao
import com.example.wikiapp.db.dao.FeaturedImageDao
import com.example.wikiapp.db.dao.FeaturedImageRemoteKeysDao
import com.example.wikiapp.db.dao.RandomArticleDao
import com.example.wikiapp.db.dao.RandomArticleRemoteKeyDao
import com.example.wikiapp.utils.ApiRoutes.BASE_URL_COMMON
import com.example.wikiapp.utils.ApiRoutes.BASE_URL_WIKI
import com.example.wikiapp.utils.ApiRoutes.COMMONS_RETROFIT
import com.example.wikiapp.utils.ApiRoutes.WIKI_DB
import com.example.wikiapp.utils.ApiRoutes.WIKI_RETROFIT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    @Named(WIKI_RETROFIT)
    fun provideWikiRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL_WIKI).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()

    @Provides
    @Singleton
    @Named(COMMONS_RETROFIT)
    fun provideCommonsRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL_COMMON)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideWikiApi(@Named(WIKI_RETROFIT) retrofit: Retrofit): WikiApi =
        retrofit.create(WikiApi::class.java)

    @Provides
    @Singleton
    fun provideCommonsApi(@Named(COMMONS_RETROFIT) retrofit: Retrofit): CommonsApi =
        retrofit.create(CommonsApi::class.java)

    // Room DB
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase = Room.databaseBuilder(
        app, AppDatabase::class.java, WIKI_DB
    ).build()

    @Provides
    fun provideFeaturedImageDao(db: AppDatabase): FeaturedImageDao = db.featuredImageDao()

    @Provides
    fun provideFeaturedImageRemoteKeysDao(db: AppDatabase): FeaturedImageRemoteKeysDao =
        db.featuredImageRemoteKeysDao()

    @Provides
    fun provideCategoryDao(db: AppDatabase): CategoryDao = db.categoryDao()

    @Provides
    fun provideCategoryRemoteKeyDao(db: AppDatabase): CategoryRemoteKeyDao =
        db.categoryRemoteKeyDao()

    @Provides
    fun provideRandomArticleDao(db: AppDatabase): RandomArticleDao = db.randomArticleDao()

    @Provides
    fun provideRandomArticleRemoteKeyDao(db: AppDatabase): RandomArticleRemoteKeyDao =
        db.randomArticleRemoteKeyDao()

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPrefs {
        return SharedPrefs(context)
    }
}