package com.deathhit.data.thumbnail.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.deathhit.core.database.model.BreedEntity
import com.deathhit.core.database.model.BreedThumbnailRefEntity
import com.deathhit.core.database.model.ThumbnailEntity
import com.deathhit.data.thumbnail.data_source.ImageRemoteDataSource
import com.deathhit.data.thumbnail.data_source.ThumbnailLocalDataSource

@ExperimentalPagingApi
internal class ThumbnailRemoteMediator(
    private val imageRemoteDataSource: ImageRemoteDataSource,
    private val thumbnailLocalDataSource: ThumbnailLocalDataSource
) : RemoteMediator<Int, ThumbnailEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ThumbnailEntity>
    ): MediatorResult {
        return try {
            // The network load method takes an optional String
            // parameter. For every page after the first, pass the String
            // token returned from the previous page to let it continue
            // from where it left off. For REFRESH, pass to load the
            // first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> ImageRemoteDataSource.PAGE_DEFAULT
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND -> return MediatorResult.Success(true)
                // Query remoteKeyDao for the next RemoteKey.
                LoadType.APPEND ->
                    // You must explicitly check if the page key is null when
                    // appending, since null is only valid for initial load.
                    // If you receive null for APPEND, that means you have
                    // reached the end of pagination and there are no more
                    // items to load.
                    thumbnailLocalDataSource.getNextThumbnailPageIndex()
                        ?: return MediatorResult.Success(
                            true
                        )
            }

            // Suspending network load via Retrofit. This doesn't need to
            // be wrapped in a withContext(Dispatcher.IO) { ... } block
            // since Retrofit's Coroutine CallAdapter dispatches on a
            // worker thread.
            val imageList =
                imageRemoteDataSource.fetchImageByPage(loadKey, state.config.pageSize)

            val breeds = ArrayList<BreedEntity>(imageList.size)
            val breedThumbnailRefs = ArrayList<BreedThumbnailRefEntity>(imageList.size)
            for (record in imageList) {
                breedThumbnailRefs.addAll(record.breeds.map {
                    BreedThumbnailRefEntity(it.id, record.id)
                })
                breeds.addAll(record.breeds.map {
                    BreedEntity(
                        it.id,
                        it.bred_for,
                        it.breed_group,
                        it.name,
                        it.life_span,
                        it.temperament
                    )
                })
            }
            val thumbnails = imageList.map {
                ThumbnailEntity(it.id, it.url)
            }

            // Store loaded data, and next key in transaction, so that
            // they're always consistent.
            thumbnailLocalDataSource.insertThumbnailPage(
                breeds,
                breedThumbnailRefs,
                loadType == LoadType.REFRESH,
                loadKey,
                thumbnails
            )

            MediatorResult.Success(imageList.isEmpty())
        } catch (e: Throwable) {
            MediatorResult.Error(e)
        }
    }
}