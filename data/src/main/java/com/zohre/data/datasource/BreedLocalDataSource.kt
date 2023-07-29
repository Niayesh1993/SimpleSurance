package com.zohre.data.datasource

import com.zohre.domain.model.BreedImages

/**
 * Handles local caching of breeds images data.
 */
interface BreedLocalDataSource {

    /**
     * This method can add all the passed data to the already existing cache of images.
     *
     * Note that it doesn't remove older caches.
     */
    fun updateBreedsImages(breedImages: BreedImages, breedTitle: String)

    fun fetchBreedsImages(breedTitle: String): Result<BreedImages>
}