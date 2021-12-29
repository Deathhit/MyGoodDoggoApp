package com.deathhit.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deathhit.domain.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Query("DELETE FROM RemoteKeyEntity")
    suspend fun clearAll()

    @Query("SELECT * FROM RemoteKeyEntity WHERE :label = label")
    suspend fun getByLabel(label: String): RemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(entity: RemoteKeyEntity)
}