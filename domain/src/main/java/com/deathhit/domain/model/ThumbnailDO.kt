package com.deathhit.domain.model

import com.deathhit.domain.database.entity.ThumbnailEntity

class ThumbnailDO(thumbnailId: String, thumbnailUrl: String) : ThumbnailEntity(
    thumbnailId,
    thumbnailUrl
)