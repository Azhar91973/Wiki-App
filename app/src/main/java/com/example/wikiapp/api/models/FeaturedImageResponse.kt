package com.example.wikiapp.api.models

import com.google.gson.annotations.SerializedName

data class FeaturedImagesResponse(
    @SerializedName("continue") val continueData: ContinueData? = null,
    @SerializedName("query") val query: QueryData? = null
)

data class ContinueData(
    @SerializedName("gcmcontinue") val gcmcontinue: String? = null,
    @SerializedName("continue") val continueStr: String? = null
)

data class QueryData(
    @SerializedName("pages") val pages: Map<String, PageData>? = null
)

data class PageData(
    @SerializedName("pageid") val pageid: Int,
    @SerializedName("ns") val ns: Int,
    @SerializedName("title") val title: String,
    @SerializedName("imagerepository") val imagerepository: String? = null,
    @SerializedName("imageinfo") val imageinfo: List<ImageInfo>? = null
)

data class ImageInfo(
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("user") val user: String,
    @SerializedName("url") val url: String,
    @SerializedName("descriptionurl") val descriptionurl: String? = null,
    @SerializedName("descriptionshorturl") val descriptionshorturl: String? = null
)
