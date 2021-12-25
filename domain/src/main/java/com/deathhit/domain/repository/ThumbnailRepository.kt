package com.deathhit.domain.repository

import androidx.paging.Pager
import com.deathhit.domain.model.ThumbnailDO

interface ThumbnailRepository {
    fun getThumbnailPager(): Pager<Int, ThumbnailDO>
}