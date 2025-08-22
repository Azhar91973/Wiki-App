package com.example.wikiapp.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.wikiapp.api.WikiApi
import com.example.wikiapp.db.AppDatabase
import com.example.wikiapp.db.entities.CategoryEntity
import com.example.wikiapp.db.entities.CategoryRemoteKeyEntity
import com.example.wikiapp.utils.Constants.CATEGORY_REMOTE_KEY

@OptIn(ExperimentalPagingApi::class)
class CategoriesRemoteMediator(
    private val api: WikiApi, private val db: AppDatabase
) : RemoteMediator<Int, CategoryEntity>() {
    private val categoryDao = db.categoryDao()
    private val categoryRemoteKeyDao = db.categoryRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, CategoryEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.APPEND -> {
                    val remoteKey = db.withTransaction {
                        categoryRemoteKeyDao.remoteKeyByCategory(CATEGORY_REMOTE_KEY)
                    }
                    if (remoteKey?.nextPage == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextPage
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            }
            val response = api.getCategories(accontinue = loadKey)
            // mapping the response to CategoryEntity
            val categories = response.query.allcategories.map { cat ->
                CategoryEntity(category = cat.category)
            }
            // getting the next page key
            val nextPage = response.`continue`.accontinue

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    categoryDao.clearAll()
                    categoryRemoteKeyDao.clearAll()
                }
                // inserting the remoteKey into the db
                categoryRemoteKeyDao.insertOrReplace(CategoryRemoteKeyEntity(CATEGORY_REMOTE_KEY, nextPage))
                // inserting the categories into the db
                categoryDao.insertAll(categories)
            }
            MediatorResult.Success(endOfPaginationReached = nextPage == null)
        } catch (e: Exception) {
            Log.e("CategoriesRemoteMediator", "Error fetching data: ${e.message}", e)
            MediatorResult.Error(e)
        }
    }
}
