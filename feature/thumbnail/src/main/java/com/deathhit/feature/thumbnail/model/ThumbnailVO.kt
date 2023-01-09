package com.deathhit.feature.thumbnail.model

import android.os.Parcelable
import com.deathhit.data.thumbnail.ThumbnailDO
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThumbnailVO(val thumbnailId: String, val thumbnailUrl: String) : Parcelable

fun ThumbnailDO.toVO() = ThumbnailVO(thumbnailId, thumbnailUrl)