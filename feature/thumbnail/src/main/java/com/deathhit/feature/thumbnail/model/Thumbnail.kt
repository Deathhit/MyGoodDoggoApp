package com.deathhit.feature.thumbnail.model

import com.deathhit.data.thumbnail.ThumbnailDO

data class Thumbnail(val thumbnailId: String, val thumbnailUrl: String)

fun ThumbnailDO.toThumbnail() = Thumbnail(thumbnailId, thumbnailUrl)
