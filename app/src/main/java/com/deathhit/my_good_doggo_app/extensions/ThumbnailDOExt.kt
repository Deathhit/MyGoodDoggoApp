package com.deathhit.my_good_doggo_app.extensions

import com.deathhit.domain.model.ThumbnailDO
import com.deathhit.my_good_doggo_app.model.ThumbnailVO

fun ThumbnailDO.toVO() = ThumbnailVO(thumbnailId, thumbnailUrl)