package com.example.simplesuranceapplication.ui.imageslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplesuranceapplication.ui.breedlist.states.BreedUiState
import com.example.simplesuranceapplication.ui.imageslist.states.BreedImageUiState
import com.example.simplesuranceapplication.utils.noBreedFound
import com.zohre.domain.usecase.GetBreedImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedImageViewModel @Inject constructor(
    private val getBreedImagesUseCase: GetBreedImagesUseCase
) : ViewModel() {

    private val _breedImageState = MutableStateFlow<BreedImageUiState>(BreedImageUiState.Loading)
    val breedImageState: StateFlow<BreedImageUiState> = _breedImageState


     fun loadBreedImages(breedTitle: String?) {
        viewModelScope.launch {
            getBreedImagesUseCase.execute(breedTitle?: "").collect{ result ->
                if (result.getOrNull() != null) {
                    _breedImageState.value = BreedImageUiState.BreedImagesAvailable(result.getOrNull())
                } else {
                    _breedImageState.value = BreedImageUiState.Failure(noBreedFound.message)
                }
            }
        }
    }
}