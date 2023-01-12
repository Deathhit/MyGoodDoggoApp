package com.deathhit.core.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.deathhit.core.database.model.ThumbnailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ThumbnailDao {
    @Query("DELETE FROM ThumbnailEntity")
    suspend fun clearAll()

    @Query("SELECT * FROM ThumbnailEntity WHERE thumbnailId = :thumbnailId")
    fun getEntityById(thumbnailId: String) : Flow<ThumbnailEntity?>

    @Query("SELECT * FROM ThumbnailEntity")
    fun getPagingSource(): PagingSource<Int, ThumbnailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(entities: List<ThumbnailEntity>)
}