package com.zohre.data.datasource

import com.zohre.data.api.BreedApiService
import com.zohre.data.api.adapter.convertToBreed
import com.zohre.domain.model.Breed
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedRemoteDataSourceImpl @Inject constructor(
    private val apiService: BreedApiService
) : BreedRemoteDataSource{

    companion object {
        const val SUCCESS_STATUS = "success"
    }

    override suspend fun fetchBreeds(): Result<Breed> {
        val response = apiService.getBreeds()

        return if (response.status == SUCCESS_STATUS){
            Result.success(response.convertToBreed())
        }else {
            Result.failure(RuntimeException(response.status))
        }
    }
}