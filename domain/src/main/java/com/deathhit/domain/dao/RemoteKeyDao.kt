package com.deathhit.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deathhit.domain.model.RemoteKeyDO

@Dao
interface RemoteKeyDao {
    @Query("DELETE FROM RemoteKeyDO")
    suspend fun clearAll()

    @Query("SELECT * FROM RemoteKeyDO WHERE :label = label")
    suspend fun getByLabel(label: String): RemoteKeyDO?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(DO: RemoteKeyDO)
}