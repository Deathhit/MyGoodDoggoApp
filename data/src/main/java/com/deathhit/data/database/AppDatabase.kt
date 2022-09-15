package com.deathhit.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.deathhit.data.database.dao.BreedDao
import com.deathhit.data.database.dao.BreedThumbnailRefDao
import com.deathhit.data.database.dao.RemoteKeyDao
import com.deathhit.data.database.dao.ThumbnailDao
import com.deathhit.data.database.model.BreedEntity
import com.deathhit.data.database.model.BreedThumbnailRefEntity
import com.deathhit.data.database.model.RemoteKeyEntity
import com.deathhit.data.database.model.ThumbnailEntity

@Database(
    entities = [BreedEntity::class, BreedThumbnailRefEntity::class, ThumbnailEntity::class, RemoteKeyEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private const val FILE_NAME = "database_c30c94dbd10244aaa30fd3a7454bbd3c"

        fun create(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, FILE_NAME).build()
    }

    abstract fun breedDao(): BreedDao
    abstract fun breedThumbnailRefDao(): BreedThumbnailRefDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun thumbnailDao(): ThumbnailDao
}