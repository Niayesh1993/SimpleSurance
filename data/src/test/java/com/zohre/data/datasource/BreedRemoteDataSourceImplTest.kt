package com.zohre.data.datasource

import com.zohre.data.api.BreedApiService
import com.zohre.data.api.model.ApiBreedImagesResponse
import com.zohre.data.api.model.ApiBreedResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class BreedRemoteDataSourceImplTest {

    @MockK
    lateinit var apiService: BreedApiService

    private lateinit var remoteDataSource: BreedRemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        remoteDataSource = BreedRemoteDataSourceImpl(apiService)
    }

    @Test
    fun `test fetch breeds api call`() = runBlocking {
        coEvery {apiService.getBreeds() } returns ApiBreedResponse(
            breedDto =  mapOf(),
            status = "success"
        )

        val response = remoteDataSource.fetchBreeds()

        coVerify { apiService.getBreeds() }
        assert(response.isSuccess)
    }

    @Test
    fun `test fetch breed failure response`() = runBlocking {
        coEvery {apiService.getBreeds() } returns ApiBreedResponse(
            breedDto =  mapOf(),
            status = "error"
        )

        val response = remoteDataSource.fetchBreeds()

        coVerify { apiService.getBreeds() }
        assert(response.isFailure)
    }

    @Test
    fun `test fetch images api call`() = runBlocking {
        coEvery {apiService.getBreedImages(any()) } returns ApiBreedImagesResponse(
            images =  emptyList(),
            status = "success"
        )

        val response = remoteDataSource.fetchBreedsImages("hound")

        coVerify { apiService.getBreedImages("hound") }
        assert(response.isSuccess)
    }

    @Test
    fun `test fetch images failure response`() = runBlocking {
        coEvery {apiService.getBreedImages(any()) } returns ApiBreedImagesResponse(
            images =  emptyList(),
            status = "error"
        )

        val response = remoteDataSource.fetchBreedsImages("hound")

        coVerify { apiService.getBreedImages("hound") }
        assert(response.isFailure)
    }

    @After
    fun finalize() {
        unmockkAll()
    }
}