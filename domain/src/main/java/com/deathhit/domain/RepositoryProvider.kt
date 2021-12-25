package com.deathhit.domain

import android.content.Context
import com.deathhit.domain.repository.BreedRepository
import com.deathhit.domain.repository.BreedRepositoryImp
import com.deathhit.domain.repository.ThumbnailRepositoryImp
import com.deathhit.domain.repository.ThumbnailRepository

object RepositoryProvider {
    @Volatile
    private var breedRepository: BreedRepository? = null

    @Volatile
    private var thumbnailRepository: ThumbnailRepository? = null

    fun getBreedRepository(context: Context): BreedRepository =
        breedRepository ?: synchronized(this) {
            breedRepository ?: createBreedRepository(context)
                .also { breedRepository = it }
        }

    fun getThumbnailRepository(context: Context): ThumbnailRepository =
        thumbnailRepository ?: synchronized(this) {
            thumbnailRepository ?: createThumbnailRepository(context)
                .also { thumbnailRepository = it }
        }

    private fun createBreedRepository(context: Context): BreedRepository {
        return BreedRepositoryImp(context)
    }

    private fun createThumbnailRepository(context: Context): ThumbnailRepository {
        return ThumbnailRepositoryImp(context)
    }
}