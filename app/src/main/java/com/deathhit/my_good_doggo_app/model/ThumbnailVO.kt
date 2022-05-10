package com.deathhit.my_good_doggo_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThumbnailVO(val thumbnailId: String, val thumbnailUrl: String) : Parcelable