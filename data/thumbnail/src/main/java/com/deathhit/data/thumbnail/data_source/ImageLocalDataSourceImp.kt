package com.deathhit.data.thumbnail.data_source

import androidx.room.withTransaction
import com.deathhit.core.database.AppDatabase
import com.deathhit.core.database.model.BreedEntity
import com.deathhit.core.database.model.BreedThumbnailRefEntity
import com.deathhit.core.database.model.RemoteKeyEntity
import com.deathhit.core.database.model.ThumbnailEntity
import com.deathhit.core.dog_api.response.Image

internal class ImageLocalDataSourceImp(private val appDatabase: AppDatabase) :
    ImageLocalDataSource {
    companion object {
        private const val REMOTE_KEY_LABEL = "62c50cde2d404e13bad1f2128c29988f"
    }

    override suspend fun getNextPage(): Int? = with(appDatabase) {
        withTransaction {
            remoteKeyDao()
                .getByLabel(REMOTE_KEY_LABEL)
        }?.nextKey
    }

    override suspend fun insertImagePage(
        imageList: List<Image>,
        isRefreshing: Boolean,
        page: Int
    ) = with(appDatabase) {
        // Store loaded data, and next key in transaction, so that
        // they're always consistent.
        withTransaction {
            if (isRefreshing) {
                breedDao().clearAll()
                breedThumbnailRefDao().clearAll()
                thumbnailDao().clearAll()
                remoteKeyDao().clearAll()
            }

            // Update RemoteKey for this query.
            remoteKeyDao().insertOrReplace(
                RemoteKeyEntity(REMOTE_KEY_LABEL, page + 1)
            )

            // Insert the new data into database, which invalidates the
            // current PagingData, allowing Paging to present the updates
            // in the DB.
            val breedRefs = ArrayList<BreedThumbnailRefEntity>(imageList.size)
            val breeds = ArrayList<BreedEntity>(imageList.size)
            for (record in imageList) {
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
            breedThumbnailRefDao().insertOrReplaceAll(breedRefs)
            breedDao().insertOrReplaceAll(breeds)

            val thumbnails = imageList.map {
                ThumbnailEntity(it.id, it.url)
            }
            thumbnailDao().insertOrReplaceAll(thumbnails)
        }
    }

}