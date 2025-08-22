package com.example.wikiapp.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.wikiapp.api.WikiApi
import com.example.wikiapp.db.AppDatabase
import com.example.wikiapp.db.entities.RandomArticleEntity
import com.example.wikiapp.db.entities.RandomArticleRemoteKeyEntity
import com.example.wikiapp.utils.Constants.RANDOM_ARTICLE_REMOTE_KEY

@OptIn(ExperimentalPagingApi::class)
class RandomArticleRemoteMediator(
    private val api: WikiApi, private val db: AppDatabase
) : RemoteMediator<Int, RandomArticleEntity>() {

    private val randomArticleDao = db.randomArticleDao()
    private val randomArticleRemoteKeyDao = db.randomArticleRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, RandomArticleEntity>
    ): MediatorResult {
        return try {
            val pageKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = db.withTransaction {
                        randomArticleRemoteKeyDao.getLastKey(RANDOM_ARTICLE_REMOTE_KEY)
                    }
                    if (remoteKeys == null) return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    remoteKeys
                }
            }
            val response = api.getRandomArticles(
                imContinue = pageKey?.imContinue,
                grnContinue = pageKey?.grnContinue,
                cont = pageKey?.continueToken
            )
            val articles = response.query?.pages?.map { pages ->
                RandomArticleEntity(
                    pageId = pages.value.pageid,
                    title = pages.value.title,
                    content = pages.value.revisions?.firstOrNull()?.content?.let { content ->
                        if (content.length >= 50) content.substring(0, 50)
                        else content
                    },
                    imageUrl = pages.value.images?.firstOrNull()?.title
                )
            } ?: emptyList()
            // Getting  next key
            val nextKey = response.`continue`
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    randomArticleDao.clearAll()
                    randomArticleRemoteKeyDao.clearRemoteKeys()
                }
                randomArticleDao.insertAll(articles)
                randomArticleRemoteKeyDao.insertOrReplace(
                    RandomArticleRemoteKeyEntity(
                        randomArticleId = RANDOM_ARTICLE_REMOTE_KEY,
                        continueToken = nextKey?.continueParam,
                        grnContinue = nextKey?.grncontinue,
                        imContinue = nextKey?.imcontinue
                    )
                )
            }

            MediatorResult.Success(endOfPaginationReached = nextKey == null)
        } catch (e: Exception) {
            Log.e("RandomArticleMediator", "Error fetching data: ${e.message}", e)
            MediatorResult.Error(e)
        }
    }
}