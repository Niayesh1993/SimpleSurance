package com.example.simplesuranceapplication.ui.breedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplesuranceapplication.ui.breedlist.states.BreedUiState
import com.example.simplesuranceapplication.utils.noBreedFound
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

     val favoriteBreedList = ArrayList<String>()

    init {
        viewModelScope.launch {
            fetchBreedList()
        }
    }
     fun fetchBreedList() {
        viewModelScope.launch {
            getBreedsUseCase.execute().collect{ result ->
                if (result.getOrNull() != null) {
                    _breedState.value = BreedUiState.BreedAvailable(result.getOrNull())
                } else {
                    _breedState.value = BreedUiState.Failure(noBreedFound.message)
                }
            }
        }
    }

     fun addBreedToFavorite(breedTitle: String){
         favoriteBreedList.add(breedTitle)
     }

     fun removeBreedFromFavorite(breedTitle: String){
         favoriteBreedList.remove(breedTitle)
     }

}