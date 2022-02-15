package com.deathhit.domain.repository

import androidx.paging.*
import androidx.room.withTransaction
import com.deathhit.data_source_dog_api.ApiService
import com.deathhit.domain.DomainDatabase
import com.deathhit.domain.entity.BreedEntity
import com.deathhit.domain.entity.BreedThumbnailRefEntity
import com.deathhit.domain.entity.ThumbnailEntity
import com.deathhit.domain.entity.RemoteKeyEntity
import com.deathhit.domain.model.ThumbnailDO
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class ThumbnailRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val domainDatabase: DomainDatabase
) : ThumbnailRepository {
    companion object {
        private const val PAGE_SIZE = 25
        private const val REMOTE_KEY_LABEL = "62c50cde2d404e13bad1f2128c29988f"
    }

    @ExperimentalPagingApi
    private class ThumbnailRemoteMediator(
        private val apiService: ApiService,
        private val domainDatabase: DomainDatabase
    ) :
        RemoteMediator<Int, ThumbnailDO>() {
        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, ThumbnailDO>
        ): MediatorResult {
            return try {
                // The network load method takes an optional String
                // parameter. For every page after the first, pass the String
                // token returned from the previous page to let it continue
                // from where it left off. For REFRESH, pass null to load the
                // first page.
                val loadKey = when (loadType) {
                    LoadType.REFRESH -> null
                    // In this example, you never need to prepend, since REFRESH
                    // will always load the first page in the list. Immediately
                    // return, reporting end of pagination.
                    LoadType.PREPEND -> return MediatorResult.Success(true)
                    // Query remoteKeyDao for the next RemoteKey.
                    LoadType.APPEND -> {
                        val remoteKey = domainDatabase.withTransaction {
                            domainDatabase.remoteKeyDao().getByLabel(REMOTE_KEY_LABEL)
                        }

                        // You must explicitly check if the page key is null when
                        // appending, since null is only valid for initial load.
                        // If you receive null for APPEND, that means you have
                        // reached the end of pagination and there are no more
                        // items to load.
                        if (remoteKey?.nextKey == null)
                            return MediatorResult.Success(true)

                        remoteKey.nextKey
                    }
                }

                // Suspending network load via Retrofit. This doesn't need to
                // be wrapped in a withContext(Dispatcher.IO) { ... } block
                // since Retrofit's Coroutine CallAdapter dispatches on a
                // worker thread.
                val response =
                    apiService.searchImage(
                        ApiService.SIZE_THUMB,
                        true,
                        ApiService.ORDER_DESC,
                        loadKey,
                        state.config.pageSize
                    )

                // Store loaded data, and next key in transaction, so that
                // they're always consistent.
                domainDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        domainDatabase.breedDao().clearAll()
                        domainDatabase.breedThumbnailRefDao().clearAll()
                        domainDatabase.thumbnailDao().clearAll()
                        domainDatabase.remoteKeyDao().clearAll()
                    }

                    // Update RemoteKey for this query.
                    val nexKey = if (loadKey == null) ApiService.PAGE_DEFAULT + 1 else loadKey + 1
                    domainDatabase.remoteKeyDao().insertOrReplace(
                        RemoteKeyEntity(REMOTE_KEY_LABEL, nexKey)
                    )

                    // Insert the new data into database, which invalidates the
                    // current PagingData, allowing Paging to present the updates
                    // in the DB.
                    val breedRefs = ArrayList<BreedThumbnailRefEntity>(response.size)
                    val breeds = ArrayList<BreedEntity>(response.size)
                    for (record in response) {
                        breedRefs.addAll(record.breeds.map {
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
                    domainDatabase.breedThumbnailRefDao().insertOrReplaceAll(breedRefs)
                    domainDatabase.breedDao().insertOrReplaceAll(breeds)

                    val thumbnails = response.map {
                        ThumbnailEntity(it.id, it.url)
                    }
                    domainDatabase.thumbnailDao().insertOrReplaceAll(thumbnails)
                }

                MediatorResult.Success(response.isEmpty())
            } catch (e: IOException) {
                MediatorResult.Error(e)
            } catch (e: HttpException) {
                MediatorResult.Error(e)
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getThumbnailPager(): Pager<Int, ThumbnailDO> {
        return Pager(
            PagingConfig(PAGE_SIZE),
            null,
            createThumbnailRemoteMediator()
        ) { domainDatabase.thumbnailDao().getPagingSource() }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun createThumbnailRemoteMediator(): RemoteMediator<Int, ThumbnailDO> =
        ThumbnailRemoteMediator(apiService, domainDatabase)
}