package com.example.wikiapp.api

import com.example.wikiapp.api.models.FeaturedImagesResponse
import com.example.wikiapp.utils.ApiRoutes.FEATURED_IMAGES

import retrofit2.http.GET
import retrofit2.http.Query

interface CommonsApi {
    @GET(FEATURED_IMAGES)
    suspend fun getFeaturedImages(
        @Query("gcmcontinue") gcmcontinue: String? = null
    ): FeaturedImagesResponse
}