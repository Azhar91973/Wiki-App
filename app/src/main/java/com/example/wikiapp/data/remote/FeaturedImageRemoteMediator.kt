package com.example.wikiapp.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.wikiapp.api.CommonsApi
import com.example.wikiapp.db.AppDatabase
import com.example.wikiapp.db.entities.FeaturedImageEntity
import com.example.wikiapp.db.entities.FeaturedImageRemoteKeys
import com.example.wikiapp.utils.Constants.FEATURED_IMAGE_REMOTE_KEY

@OptIn(ExperimentalPagingApi::class)
class FeaturedImageRemoteMediator(
    private val api: CommonsApi, private val db: AppDatabase
) : RemoteMediator<Int, FeaturedImageEntity>() {

    val featuredImageDao = db.featuredImageDao()
    val featuredImageRemoteKeysDao = db.featuredImageRemoteKeysDao()
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, FeaturedImageEntity>
    ): MediatorResult {
        return try {
            val pageKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = db.withTransaction {
                        featuredImageRemoteKeysDao.getLastKey(FEATURED_IMAGE_REMOTE_KEY)
                    }
                    if (remoteKeys?.nextPage == null) return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    remoteKeys.nextPage

                }
            }
            val response = api.getFeaturedImages(gcmcontinue = pageKey)
            // mapping the response to FeaturedImageEntity
            val images = response.query?.pages?.values?.map { page ->
                val info = page.imageinfo?.firstOrNull()
                FeaturedImageEntity(
                    pageId = page.pageid,
                    title = page.title,
                    timestamp = info?.timestamp ?: "",
                    user = info?.user ?: "",
                    url = info?.url ?: ""
                )
            } ?: emptyList()
            // getting the next page key
            val nextPage = response.continueData?.gcmcontinue

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.featuredImageDao().clearAll()
                    db.featuredImageDao().deletePrimaryKeyIndex()  // re-setting the pk value
                    db.featuredImageRemoteKeysDao().clearRemoteKeys()
                }
                // inserting the images into the db
                featuredImageDao.insertAll(images)
                // inserting the remoteKey into the db
                featuredImageRemoteKeysDao.insertOrReplace(
                    FeaturedImageRemoteKeys(
                        FEATURED_IMAGE_REMOTE_KEY, nextPage
                    )
                )

            }
            MediatorResult.Success(endOfPaginationReached = nextPage == null)
        } catch (e: Exception) {
            Log.e("FeaturedRemoteMediator", "Error fetching data: ${e.message}", e)
            MediatorResult.Error(e)
        }
    }
}