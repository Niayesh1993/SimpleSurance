package com.example.simplesuranceapplication.ui.imageslist.states

import com.zohre.domain.model.BreedImages

sealed class BreedImageUiState {

    object Loading : BreedImageUiState()

    class Failure(val message: String?) : BreedImageUiState()

    class BreedImagesAvailable(val breedImages: BreedImages?) : BreedImageUiState()
}
