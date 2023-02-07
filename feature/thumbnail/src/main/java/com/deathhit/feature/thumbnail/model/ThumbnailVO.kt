package com.deathhit.feature.thumbnail.model

import com.deathhit.data.thumbnail.ThumbnailDO

data class ThumbnailVO(val thumbnailId: String, val thumbnailUrl: String)

fun ThumbnailDO.toThumbnailVO() = ThumbnailVO(thumbnailId, thumbnailUrl)
