package com.deathhit.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deathhit.data.database.Column

@Entity
data class RemoteKeyEntity(
    @PrimaryKey @ColumnInfo(name = Column.LABEL) val label: String,
    @ColumnInfo(name = Column.NEXT_KEY) val nextKey: Int?,
)