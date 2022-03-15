package com.deathhit.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deathhit.domain.model.BreedThumbnailRefDO

@Dao
interface BreedThumbnailRefDao {
    @Query("DELETE FROM BreedThumbnailRefDO")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(DOList: List<BreedThumbnailRefDO>)
}