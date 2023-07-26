package com.zohre.domain.usecase

import com.zohre.domain.model.Breed
import com.zohre.domain.repository.BreedRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetBreedsUseCase @Inject constructor(
    private val repository: BreedRepository
) {

    fun execute(): Flow<Result<Breed>> =
        repository.getBreeds()
}