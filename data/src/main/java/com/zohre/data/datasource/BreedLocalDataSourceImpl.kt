package com.zohre.data.datasource

import com.zohre.domain.model.BreedImages
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedLocalDataSourceImpl @Inject constructor(): BreedLocalDataSource {

    private val cachedImages = mutableMapOf<String, BreedImages>()

    override fun updateBreedsImages(breedImages: BreedImages, breedTitle: String) {
        cachedImages[breedTitle] = breedImages
    }

    override fun fetchBreedsImages(breedTitle: String): Result<BreedImages> {
        val result = cachedImages[breedTitle]
        return if (result == null) {
            Result.failure(RuntimeException("Cache data not found"))
        } else {
            Result.success(result)
        }
    }
}