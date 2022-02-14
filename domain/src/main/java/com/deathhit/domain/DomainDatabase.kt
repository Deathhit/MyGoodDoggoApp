package com.deathhit.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deathhit.domain.dao.BreedDao
import com.deathhit.domain.dao.BreedThumbnailRefDao
import com.deathhit.domain.dao.ThumbnailDao
import com.deathhit.domain.dao.RemoteKeyDao
import com.deathhit.domain.entity.BreedEntity
import com.deathhit.domain.entity.BreedThumbnailRefEntity
import com.deathhit.domain.entity.ThumbnailEntity
import com.deathhit.domain.entity.RemoteKeyEntity

@Database(
    entities = [BreedEntity::class, BreedThumbnailRefEntity::class, ThumbnailEntity::class, RemoteKeyEntity::class],
    version = 1
)
abstract class DomainDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun breedThumbnailRefDao(): BreedThumbnailRefDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun thumbnailDao(): ThumbnailDao
}