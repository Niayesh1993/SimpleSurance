package com.zohre.data.datasource

import com.zohre.data.api.BreedApiService
import com.zohre.data.api.model.ApiBreedResponse
import com.zohre.data.api.model.BreedDto
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
            breedDto = BreedDto(
                data = mapOf()
            ),
            status = "success"
        )

        val response = remoteDataSource.fetchBreeds()

        coVerify { apiService.getBreeds() }
        assert(response.isSuccess)
    }

    @Test
    fun `test fetch breed failure response`() = runBlocking {
        coEvery {apiService.getBreeds() } returns ApiBreedResponse(
            breedDto = BreedDto(
                data = mapOf()
            ),
            status = "error"
        )

        val response = remoteDataSource.fetchBreeds()

        coVerify { apiService.getBreeds() }
        assert(response.isFailure)
    }

    @After
    fun finalize() {
        unmockkAll()
    }
}