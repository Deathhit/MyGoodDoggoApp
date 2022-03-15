package com.deathhit.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deathhit.domain.dao.BreedDao
import com.deathhit.domain.dao.BreedThumbnailRefDao
import com.deathhit.domain.dao.ThumbnailDao
import com.deathhit.domain.dao.RemoteKeyDao
import com.deathhit.domain.model.BreedDO
import com.deathhit.domain.model.BreedThumbnailRefDO
import com.deathhit.domain.model.ThumbnailDO
import com.deathhit.domain.model.RemoteKeyDO

@Database(
    entities = [BreedDO::class, BreedThumbnailRefDO::class, ThumbnailDO::class, RemoteKeyDO::class],
    version = 1
)
abstract class DomainDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun breedThumbnailRefDao(): BreedThumbnailRefDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun thumbnailDao(): ThumbnailDao
}