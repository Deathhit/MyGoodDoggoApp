package com.deathhit.domain.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.deathhit.domain.database.entity.ThumbnailEntity
import com.deathhit.domain.model.ThumbnailDO

@Dao
interface ThumbnailDao {
    @Query("DELETE FROM ThumbnailEntity")
    suspend fun clearAll()

    @Query("SELECT * FROM ThumbnailEntity")
    fun getPagingSource(): PagingSource<Int, ThumbnailDO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(entityList: List<ThumbnailEntity>)
}