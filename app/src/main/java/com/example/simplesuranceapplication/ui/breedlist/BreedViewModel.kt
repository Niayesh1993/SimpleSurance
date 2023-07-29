package com.example.simplesuranceapplication.ui.breedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplesuranceapplication.ui.breedlist.states.BreedUiModel
import com.example.simplesuranceapplication.ui.breedlist.states.BreedUiState
import com.example.simplesuranceapplication.utils.noBreedFound
import com.zohre.domain.model.Breed
import com.zohre.domain.usecase.GetBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(
    private val getBreedsUseCase: GetBreedsUseCase
) : ViewModel()
 {

    private val _breedState = MutableStateFlow<BreedUiState>(BreedUiState.Loading)
    val breedState: StateFlow<BreedUiState> = _breedState

     val favoriteBreedList = ArrayList<BreedUiModel>()

    init {
        viewModelScope.launch {
            fetchBreedList()
        }
    }
     fun fetchBreedList() {
        viewModelScope.launch {
            getBreedsUseCase.execute().collect{ result ->
                if (result.getOrNull() != null) {
                    _breedState.value = BreedUiState.BreedAvailable(result.getOrNull()!!.convertToBreedUiModel())
                } else {
                    _breedState.value = BreedUiState.Failure(noBreedFound.message)
                }
            }
        }
    }

     fun addBreedToFavorite(breedUiModel: BreedUiModel){
         favoriteBreedList.add(breedUiModel)
     }

     fun removeBreedFromFavorite(breedUiModel: BreedUiModel){
         favoriteBreedList.remove(breedUiModel)
     }

}

fun Breed.convertToBreedUiModel(): List<BreedUiModel>{
    return this.data!!.keys.map {
        BreedUiModel(it, false)
    }
}

