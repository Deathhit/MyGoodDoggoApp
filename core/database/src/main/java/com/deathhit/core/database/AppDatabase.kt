package com.deathhit.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.deathhit.core.database.dao.BreedDao
import com.deathhit.core.database.dao.BreedThumbnailRefDao
import com.deathhit.core.database.dao.RemoteKeyDao
import com.deathhit.core.database.dao.ThumbnailDao
import com.deathhit.core.database.model.BreedEntity
import com.deathhit.core.database.model.BreedThumbnailRefEntity
import com.deathhit.core.database.model.RemoteKeyEntity
import com.deathhit.core.database.model.ThumbnailEntity

@Database(
    entities = [BreedEntity::class, BreedThumbnailRefEntity::class, ThumbnailEntity::class, RemoteKeyEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private const val FILE_NAME = "database_c30c94dbd10244aaa30fd3a7454bbd3c"

        fun create(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, FILE_NAME).build()

        fun createInMemory(context: Context) =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    abstract fun breedDao(): BreedDao
    abstract fun breedThumbnailRefDao(): BreedThumbnailRefDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun thumbnailDao(): ThumbnailDao
}