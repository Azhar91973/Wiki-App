package com.example.wikiapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wikiapp.db.dao.CategoryDao
import com.example.wikiapp.db.dao.CategoryRemoteKeyDao
import com.example.wikiapp.db.dao.FeaturedImageDao
import com.example.wikiapp.db.dao.FeaturedImageRemoteKeysDao
import com.example.wikiapp.db.dao.RandomArticleDao
import com.example.wikiapp.db.dao.RandomArticleRemoteKeyDao
import com.example.wikiapp.db.entities.CategoryEntity
import com.example.wikiapp.db.entities.CategoryRemoteKeyEntity
import com.example.wikiapp.db.entities.FeaturedImageEntity
import com.example.wikiapp.db.entities.FeaturedImageRemoteKeys
import com.example.wikiapp.db.entities.RandomArticleEntity
import com.example.wikiapp.db.entities.RandomArticleRemoteKeyEntity

@Database(
    entities = [FeaturedImageEntity::class, FeaturedImageRemoteKeys::class, RandomArticleEntity::class, RandomArticleRemoteKeyEntity::class, CategoryEntity::class, CategoryRemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun featuredImageDao(): FeaturedImageDao

    abstract fun featuredImageRemoteKeysDao(): FeaturedImageRemoteKeysDao
    abstract fun randomArticleDao(): RandomArticleDao
    abstract fun randomArticleRemoteKeyDao(): RandomArticleRemoteKeyDao

    abstract fun categoryDao(): CategoryDao
    abstract fun categoryRemoteKeyDao(): CategoryRemoteKeyDao
}