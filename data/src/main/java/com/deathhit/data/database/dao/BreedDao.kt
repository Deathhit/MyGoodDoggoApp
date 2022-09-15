package com.deathhit.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deathhit.data.database.model.BreedEntity

@Dao
interface BreedDao {
    @Query("DELETE FROM BreedEntity")
    suspend fun clearAll()

    @Query("SELECT BreedEntity.* FROM BreedEntity LEFT JOIN BreedThumbnailRefEntity ON BreedEntity.breedId = BreedThumbnailRefEntity.breedId WHERE BreedThumbnailRefEntity.thumbnailId = :thumbnailId GROUP BY BreedEntity.breedId")
    suspend fun getListByThumbnailId(thumbnailId: String): List<BreedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(DOList: List<BreedEntity>)
}