package com.deathhit.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deathhit.core.database.model.BreedThumbnailRefEntity

@Dao
interface BreedThumbnailRefDao {
    @Query("DELETE FROM BreedThumbnailRefEntity")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(entities: List<BreedThumbnailRefEntity>)
}