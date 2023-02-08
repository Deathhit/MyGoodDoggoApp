package com.deathhit.core.database.dao

import androidx.room.*
import com.deathhit.core.database.Column
import com.deathhit.core.database.model.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Query("DELETE FROM BreedEntity")
    suspend fun clearAll()

    @Query("SELECT BreedEntity.* FROM BreedEntity " +
            "LEFT JOIN BreedThumbnailRefEntity " +
            "ON BreedEntity.${Column.BREED_ID} = BreedThumbnailRefEntity.${Column.BREED_ID} " +
            "WHERE BreedThumbnailRefEntity.${Column.THUMBNAIL_ID} = :thumbnailId " +
            "GROUP BY BreedEntity.${Column.BREED_ID}")
    fun getListFlowByThumbnailId(thumbnailId: String): Flow<List<BreedEntity>>

    @Upsert
    suspend fun upsert(entities: List<BreedEntity>)
}