package com.deathhit.domain.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deathhit.domain.database.Column

@Entity
open class ThumbnailEntity(
    @PrimaryKey @ColumnInfo(name = Column.THUMBNAIL_ID) val thumbnailId: String,
    @ColumnInfo(name = Column.THUMBNAIL_URL) val thumbnailUrl: String
)