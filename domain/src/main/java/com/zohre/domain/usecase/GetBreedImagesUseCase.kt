package com.zohre.domain.usecase

import com.zohre.domain.model.BreedImages
import com.zohre.domain.repository.BreedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBreedImagesUseCase @Inject constructor(
    private val breedRepository: BreedRepository
) {
    fun execute(breedTitle: String): Flow<Result<BreedImages>> =
        breedRepository.getBreedsImages(breedTitle)
}