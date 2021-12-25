package com.deathhit.domain.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.deathhit.domain.database.Column

@Entity(primaryKeys = [Column.BREED_ID, Column.THUMBNAIL_ID])
open class BreedThumbnailRefEntity(
    @ColumnInfo(name = Column.BREED_ID) val breedId: String,
    @ColumnInfo(name = Column.THUMBNAIL_ID) val thumbnailId: String
)