package com.deathhit.domain.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.deathhit.domain.model.ThumbnailDO

@Dao
interface ThumbnailDao {
    @Query("DELETE FROM ThumbnailDO")
    suspend fun clearAll()

    @Query("SELECT * FROM ThumbnailDO")
    fun getPagingSource(): PagingSource<Int, ThumbnailDO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(DOList: List<ThumbnailDO>)
}