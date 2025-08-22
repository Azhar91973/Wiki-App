package com.example.wikiapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.wikiapp.api.CommonsApi
import com.example.wikiapp.api.WikiApi
import com.example.wikiapp.data.remote.CategoriesRemoteMediator
import com.example.wikiapp.data.remote.FeaturedImageRemoteMediator
import com.example.wikiapp.data.remote.RandomArticleRemoteMediator
import com.example.wikiapp.db.AppDatabase
import com.example.wikiapp.db.entities.CategoryEntity
import com.example.wikiapp.db.entities.FeaturedImageEntity
import com.example.wikiapp.db.entities.RandomArticleEntity
import com.example.wikiapp.utils.Constants.PER_PAGE_ITEMS
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WikiRepository @Inject constructor(
    private val wikiApi: WikiApi, private val commonsApi: CommonsApi, private val db: AppDatabase
) {
    // getting the featured images from the Database
    @OptIn(ExperimentalPagingApi::class)
    fun getFeaturedImages(): Flow<PagingData<FeaturedImageEntity>> {
        return Pager(
            config = PagingConfig(pageSize = PER_PAGE_ITEMS),
            remoteMediator = FeaturedImageRemoteMediator(
                commonsApi, db
            ),
            pagingSourceFactory = { db.featuredImageDao().pagingSource() }).flow
    }

    // getting the categories from the Database
    @OptIn(ExperimentalPagingApi::class)
    fun getCategories(): Flow<PagingData<CategoryEntity>> = Pager(
        config = PagingConfig(pageSize = PER_PAGE_ITEMS),
        remoteMediator = CategoriesRemoteMediator(wikiApi, db),
        pagingSourceFactory = { db.categoryDao().pagingSource() }).flow

    // getting the random articles from the Database
    @OptIn(ExperimentalPagingApi::class)
    fun getRandomArticles(): Flow<PagingData<RandomArticleEntity>> = Pager(
        config = PagingConfig(pageSize = PER_PAGE_ITEMS),
        remoteMediator = RandomArticleRemoteMediator(wikiApi, db),
        pagingSourceFactory = { db.randomArticleDao().pagingSource() }).flow
}