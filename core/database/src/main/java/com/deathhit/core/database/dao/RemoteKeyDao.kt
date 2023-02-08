package com.deathhit.core.database.dao

import androidx.room.*
import com.deathhit.core.database.Column
import com.deathhit.core.database.model.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Query("DELETE FROM RemoteKeyEntity")
    suspend fun clearAll()

    @Query("SELECT * FROM RemoteKeyEntity WHERE :label = ${Column.LABEL}")
    @Transaction
    suspend fun getByLabel(label: String): RemoteKeyEntity?

    @Upsert
    suspend fun upsert(entity: RemoteKeyEntity)
}