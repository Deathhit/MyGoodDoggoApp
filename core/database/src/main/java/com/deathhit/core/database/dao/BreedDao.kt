package com.deathhit.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deathhit.core.database.model.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Query("DELETE FROM BreedEntity")
    suspend fun clearAll()

    @Query("SELECT BreedEntity.* FROM BreedEntity LEFT JOIN BreedThumbnailRefEntity ON BreedEntity.breedId = BreedThumbnailRefEntity.breedId WHERE BreedThumbnailRefEntity.thumbnailId = :thumbnailId GROUP BY BreedEntity.breedId")
    fun getListFlowByThumbnailId(thumbnailId: String): Flow<List<BreedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(DOList: List<BreedEntity>)
}