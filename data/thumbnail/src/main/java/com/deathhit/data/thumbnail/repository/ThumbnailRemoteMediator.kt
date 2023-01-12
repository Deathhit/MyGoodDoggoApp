package com.deathhit.data.thumbnail.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.deathhit.core.database.model.ThumbnailEntity
import com.deathhit.core.dog_api.service.ImageApiService
import com.deathhit.data.thumbnail.data_source.ImageLocalDataSource
import com.deathhit.data.thumbnail.data_source.ImageRemoteDataSource

@ExperimentalPagingApi
internal class ThumbnailRemoteMediator(
    private val imageLocalDataSource: ImageLocalDataSource,
    private val imageRemoteDataSource: ImageRemoteDataSource
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
                LoadType.REFRESH -> ImageApiService.PAGE_DEFAULT
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
                    imageLocalDataSource.getNextImagePageIndex() ?: return MediatorResult.Success(true)
            }

            // Suspending network load via Retrofit. This doesn't need to
            // be wrapped in a withContext(Dispatcher.IO) { ... } block
            // since Retrofit's Coroutine CallAdapter dispatches on a
            // worker thread.
            val imageList =
                imageRemoteDataSource.fetchImageByPage(loadKey, state.config.pageSize)

            imageLocalDataSource.insertImagePage(
                imageList,
                loadType == LoadType.REFRESH,
                loadKey
            )

            MediatorResult.Success(imageList.isEmpty())
        } catch (e: Throwable) {
            MediatorResult.Error(e)
        }
    }
}