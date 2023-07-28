package com.example.simplesuranceapplication.ui.breedlist.states

import com.zohre.domain.model.Breed

sealed class BreedUiState {

    object Loading : BreedUiState()

    class Failure(val message: String?) : BreedUiState()

    class BreedAvailable(val breeds: Breed?) : BreedUiState()
}
