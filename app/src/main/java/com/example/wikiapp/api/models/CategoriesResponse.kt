package com.example.wikiapp.api.models

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("continue") val `continue`: ContinueX, val query: Query
)

data class ContinueX(
    @SerializedName("accontinue") val accontinue: String? = null,
    @SerializedName("continue") val `continue`: String
)

data class Query(
    @SerializedName("allcategories") val allcategories: List<Allcategory>
)

data class Allcategory(
    @SerializedName("*") val category: String
)