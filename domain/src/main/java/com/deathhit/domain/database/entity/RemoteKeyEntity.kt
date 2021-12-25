package com.deathhit.domain.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deathhit.domain.database.Column

@Entity
open class RemoteKeyEntity(
    @PrimaryKey @ColumnInfo(name = Column.LABEL) val label: String,
    @ColumnInfo(name = Column.NEXT_KEY) val nextKey: Int?,
)