package com.zohre.data.api.adapter

import com.zohre.data.api.model.ApiBreedImagesResponse
import com.zohre.data.api.model.ApiBreedResponse
import com.zohre.domain.model.Breed
import com.zohre.domain.model.BreedImages

/**
 * This method converts data layer models to domain layer models.
 */
internal fun ApiBreedResponse.convertToBreed(): Breed {
 return Breed(
     this.breedDto
 )
}

internal fun ApiBreedImagesResponse.convertToBreedImages(): BreedImages {
    return BreedImages(
        this.images
    )
}