package com.deathhit.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deathhit.core.database.Column

@Entity
data class BreedEntity(
    @PrimaryKey @ColumnInfo(name = Column.BREED_ID) val breedId: String,
    @ColumnInfo(name = Column.BRED_FOR) val bredFor: String?,
    @ColumnInfo(name = Column.BREED_GROUP) val breedGroup: String?,
    @ColumnInfo(name = Column.BREED_NAME) val breedName: String?,
    @ColumnInfo(name = Column.LIFESPAN) val lifespan: String?,
    @ColumnInfo(name = Column.TEMPERAMENT) val temperament: String?
)