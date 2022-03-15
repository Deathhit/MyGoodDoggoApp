package com.deathhit.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.deathhit.domain.Column

@Entity(primaryKeys = [Column.BREED_ID, Column.THUMBNAIL_ID])
data class BreedThumbnailRefDO(
    @ColumnInfo(name = Column.BREED_ID) val breedId: String,
    @ColumnInfo(name = Column.THUMBNAIL_ID) val thumbnailId: String
)