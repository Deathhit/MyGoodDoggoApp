package com.deathhit.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.deathhit.domain.database.dao.BreedDao
import com.deathhit.domain.database.dao.BreedThumbnailRefDao
import com.deathhit.domain.database.dao.ThumbnailDao
import com.deathhit.domain.database.dao.RemoteKeyDao
import com.deathhit.domain.database.entity.BreedEntity
import com.deathhit.domain.database.entity.BreedThumbnailRefEntity
import com.deathhit.domain.database.entity.ThumbnailEntity
import com.deathhit.domain.database.entity.RemoteKeyEntity

@Database(
    entities = [BreedEntity::class, BreedThumbnailRefEntity::class, ThumbnailEntity::class, RemoteKeyEntity::class],
    version = 1
)
abstract class DomainDatabase : RoomDatabase() {
    companion object {
        private const val FILE_NAME = "database_c30c94dbd10244aaa30fd3a7454bbd3c"

        @Volatile
        private var instance: DomainDatabase? = null

        fun getInstance(context: Context): DomainDatabase =
            instance ?: synchronized(this) {
                instance ?: createInstance(context).also { instance = it }
            }

        private fun createInstance(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DomainDatabase::class.java, FILE_NAME
            ).build()
    }

    val breedDao by lazy { breedDao() }
    val breedThumbnailRefDao by lazy { breedThumbnailRefDao() }
    val remoteKeyDao by lazy { remoteKeyDao() }
    val thumbnailDao by lazy { thumbnailDao() }

    protected abstract fun breedDao(): BreedDao
    protected abstract fun breedThumbnailRefDao(): BreedThumbnailRefDao
    protected abstract fun remoteKeyDao(): RemoteKeyDao
    protected abstract fun thumbnailDao(): ThumbnailDao
}