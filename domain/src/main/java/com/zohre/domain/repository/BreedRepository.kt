package com.zohre.domain.repository

import com.zohre.domain.model.Breed
import com.zohre.domain.model.BreedImages
import kotlinx.coroutines.flow.Flow

/**
 * The implementation of this interface should be responsible for providing breeds information
 * to use cases and should be implemented in the data layer.
 */
interface BreedRepository {

    /**
     * It returns Result.success(breed) when breeds  are available.
     * It returns Result.failure() when it cannot be handled at the moment.
     */
    fun getBreeds():Flow<Result<Breed>>

    fun getBreedsImages(breedTitle: String): Flow<Result<BreedImages>>
}