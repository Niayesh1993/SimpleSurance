package com.zohre.data.api

import com.zohre.data.api.model.ApiBreedResponse
import retrofit2.http.GET

interface BreedApiService {

    @GET("breeds/list/all")
    suspend fun getBreeds(): ApiBreedResponse
}