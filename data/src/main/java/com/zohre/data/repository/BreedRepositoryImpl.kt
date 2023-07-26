package com.zohre.data.repository

import com.zohre.data.datasource.BreedRemoteDataSource
import com.zohre.data.di.IODispatcher
import com.zohre.domain.model.Breed
import com.zohre.domain.repository.BreedRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BreedRepositoryImpl @Inject constructor(
    private val breedRemoteDataSource: BreedRemoteDataSource,
    /**
     * This should be provided here because we want all of IO tasks run with a IO dispatcher so
     * all of our suspend function calls would be safe to call from main thread.
     */
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : BreedRepository {

    override fun getBreeds(): Flow<Result<Breed>> = flow {
        val remoteData = breedRemoteDataSource.fetchBreeds()
        emit(remoteData)
    }.flowOn(dispatcher)
        .catch { emit(Result.failure(it)) }
}