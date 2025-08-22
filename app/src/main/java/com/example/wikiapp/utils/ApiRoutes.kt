package com.example.wikiapp.utils

object ApiRoutes {

    const val BASE_URL_WIKI = "https://en.wikipedia.org/"
    const val BASE_URL_COMMON = "https://commons.wikimedia.org/"
    const val RANDOM_ARTICLES =
        "w/api.php?format=json&action=query&generator=random&grnnamespace=0&prop=revisions%7Cimages&rvprop=content&grnlimit=10"
    const val CATEGORIES = "w/api.php?action=query&list=allcategories&acprefix&format=json"
    const val FEATURED_IMAGES =
        "w/api.php?action=query&prop=imageinfo&iiprop=timestamp|user|url&generator=categorymembers&gcmtype=file&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&format=json&utf8="
    const val WIKI_RETROFIT = "wikiRetrofit"
    const val COMMONS_RETROFIT = "commonsRetrofit"
    const val WIKI_DB = "wiki_db"
}