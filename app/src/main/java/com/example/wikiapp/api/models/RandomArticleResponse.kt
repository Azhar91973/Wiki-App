package com.example.wikiapp.api.models

import com.google.gson.annotations.SerializedName

data class RandomArticleResponse(
    @SerializedName("continue") val `continue`: Continue?,
    @SerializedName("query") val query: QueryInfo?
)

data class Continue(
    @SerializedName("imcontinue") val imcontinue: String?,
    @SerializedName("grncontinue") val grncontinue: String?,
    @SerializedName("continue") val continueParam: String?
)

data class QueryInfo(
    @SerializedName("pages") val pages: Map<String, Page>?
)

data class Page(
    @SerializedName("pageid") val pageid: Int,
    @SerializedName("title") val title: String,
    @SerializedName("revisions") val revisions: List<Revision>?,
    @SerializedName("images") val images: List<Image>?
)

data class Revision(
    @SerializedName("*") val content: String?
)

data class Image(
    @SerializedName("title") val title: String?
)