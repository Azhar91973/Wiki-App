package com.example.wikiapp.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_remote_key")
data class CategoryRemoteKeyEntity(
    @PrimaryKey(autoGenerate = false) val category: String, val nextPage: String? = null
)
