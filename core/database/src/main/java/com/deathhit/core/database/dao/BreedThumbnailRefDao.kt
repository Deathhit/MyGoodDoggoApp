package com.deathhit.core.database.dao

import androidx.room.*
import com.deathhit.core.database.model.BreedThumbnailRefEntity

@Dao
interface BreedThumbnailRefDao {
    @Query("DELETE FROM BreedThumbnailRefEntity")
    suspend fun clearAll()

    @Upsert
    suspend fun upsert(entities: List<BreedThumbnailRefEntity>)
}