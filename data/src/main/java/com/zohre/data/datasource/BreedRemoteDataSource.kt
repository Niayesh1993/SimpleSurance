package com.zohre.data.datasource

import com.zohre.domain.model.Breed
import com.zohre.domain.model.BreedImages

/**
 * Handles retrieving remote restaurants data.
 */
interface BreedRemoteDataSource {

    /**
     * Returns Result.success(breed) if available. Returns Result.failure() if
     * breeds couldn't be retrieved.
     */
    suspend fun fetchBreeds():Result<Breed>

    suspend fun fetchBreedsImages(breedTitle: String): Result<BreedImages>
}