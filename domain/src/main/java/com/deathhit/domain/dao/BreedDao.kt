package com.deathhit.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deathhit.domain.model.BreedDO

@Dao
interface BreedDao {
    @Query("DELETE FROM BreedDO")
    suspend fun clearAll()

    @Query("SELECT BreedDO.* FROM BreedDO LEFT JOIN BreedThumbnailRefDO ON BreedDO.breedId = BreedThumbnailRefDO.breedId WHERE BreedThumbnailRefDO.thumbnailId = :thumbnailId GROUP BY BreedDO.breedId")
    suspend fun getListByThumbnailId(thumbnailId: String): List<BreedDO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(DOList: List<BreedDO>)
}