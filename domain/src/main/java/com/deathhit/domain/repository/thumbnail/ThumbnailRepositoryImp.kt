package com.deathhit.domain.repository.thumbnail

import androidx.paging.*
import com.deathhit.data_source_dog_api.ImageApiService
import com.deathhit.domain.DomainDatabase
import com.deathhit.domain.model.ThumbnailDO
import javax.inject.Inject

internal class ThumbnailRepositoryImp @Inject constructor(
    private val domainDatabase: DomainDatabase,
    private val imageApiService: ImageApiService
) : ThumbnailRepository {
    companion object {
        private const val PAGE_SIZE = 25
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getThumbnailPager(): Pager<Int, ThumbnailDO> {
        return Pager(
            PagingConfig(PAGE_SIZE),
            null,
            ThumbnailRemoteMediator(domainDatabase, imageApiService)
        ) { domainDatabase.thumbnailDao().getPagingSource() }
    }
}