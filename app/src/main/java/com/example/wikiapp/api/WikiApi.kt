package com.example.wikiapp.api


import com.example.wikiapp.api.models.CategoriesResponse
import com.example.wikiapp.api.models.RandomArticleResponse
import com.example.wikiapp.utils.ApiRoutes.CATEGORIES
import com.example.wikiapp.utils.ApiRoutes.RANDOM_ARTICLES
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiApi {
    @GET(RANDOM_ARTICLES)
    suspend fun getRandomArticles(
        @Query("continue") cont: String? = null,
        @Query("grncontinue") grnContinue: String? = null,
        @Query("imcontinue") imContinue: String? = null
    ): RandomArticleResponse

    @GET(CATEGORIES)
    suspend fun getCategories(
        @Query("accontinue") accontinue: String? = null,
    ): CategoriesResponse
}