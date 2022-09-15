package com.deathhit.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.deathhit.data.database.model.ThumbnailEntity

@Dao
interface ThumbnailDao {
    @Query("DELETE FROM ThumbnailEntity")
    suspend fun clearAll()

    @Query("SELECT * FROM ThumbnailEntity")
    fun getPagingSource(): PagingSource<Int, ThumbnailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(DOList: List<ThumbnailEntity>)
}