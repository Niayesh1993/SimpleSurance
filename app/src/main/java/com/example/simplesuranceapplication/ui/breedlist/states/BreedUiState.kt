package com.example.simplesuranceapplication.ui.breedlist.states


sealed class BreedUiState {

    object Loading : BreedUiState()

    class Failure(val message: String?) : BreedUiState()

    class BreedAvailable(val breedUiModels: List<BreedUiModel>) : BreedUiState()
}

data class BreedUiModel(
    val title: String,
    var favorite: Boolean
)
