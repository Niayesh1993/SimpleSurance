package com.zohre.data.api

import com.zohre.data.api.model.ApiBreedImagesResponse
import com.zohre.data.api.model.ApiBreedResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BreedApiService {

    @GET("api/breeds/list/all")
    suspend fun getBreeds(): ApiBreedResponse

    @GET("api/breed/{breedTitle}/images")
    suspend fun getBreedImages(
        @Path("breedTitle") breedTitle: String
    ): ApiBreedImagesResponse
}