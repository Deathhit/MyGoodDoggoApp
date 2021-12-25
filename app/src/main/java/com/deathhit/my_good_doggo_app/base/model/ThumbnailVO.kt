package com.deathhit.my_good_doggo_app.base.model

import android.os.Parcelable
import com.deathhit.domain.model.ThumbnailDO
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThumbnailVO(val thumbnailId: String, val thumbnailUrl: String) : Parcelable {
    companion object {
        fun valueOf(thumbnailDO: ThumbnailDO): ThumbnailVO {
            return ThumbnailVO(thumbnailDO.thumbnailId, thumbnailDO.thumbnailUrl)
        }
    }
}