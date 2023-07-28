package com.zohre.data.repository

import com.zohre.data.datasource.BreedRemoteDataSourceImpl
import com.zohre.domain.model.Breed
import com.zohre.domain.model.BreedImages
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class BreedRepositoryImplTest {

    @RelaxedMockK
    lateinit var remoteDataSourceImpl: BreedRemoteDataSourceImpl

    @RelaxedMockK
    lateinit var breed: Breed

    @RelaxedMockK
    lateinit var breedImages: BreedImages

    @InjectMockKs
    lateinit var breedRepositoryImpl: BreedRepositoryImpl

    private lateinit var coroutineDispatcher: TestCoroutineDispatcher

    @Before
    fun setup() {
        coroutineDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test get breed success - method call`() = coroutineDispatcher.runBlockingTest {
        coEvery {
            remoteDataSourceImpl.fetchBreeds()
        }returns Result.success(breed)

        breedRepositoryImpl.getBreeds().last()

        coVerify { remoteDataSourceImpl.fetchBreeds() }
    }

    @Test
    fun `test get breed successful response`() = coroutineDispatcher.runBlockingTest {
        coEvery {
            remoteDataSourceImpl.fetchBreeds()
        } returns Result.success(breed)

        val response = breedRepositoryImpl.getBreeds().first()

        coVerify { remoteDataSourceImpl.fetchBreeds() }
        assert(response.isSuccess)
        assert(response.getOrNull() != null)
    }

    @Test
    fun `test breed failed response`() = coroutineDispatcher.runBlockingTest {
        coEvery {
            remoteDataSourceImpl.fetchBreeds()
        } returns Result.failure(Throwable(""))

        val response = breedRepositoryImpl.getBreeds().last()

        coVerify { remoteDataSourceImpl.fetchBreeds() }
        assert(response.isFailure)
        assert(response.getOrNull() == null)
    }

    @Test
    fun `test get image successful response`() = coroutineDispatcher.runBlockingTest {
        coEvery {
            remoteDataSourceImpl.fetchBreedsImages(any())
        } returns Result.success(breedImages)

        val response = breedRepositoryImpl.getBreedsImages("hound").first()

        coVerify { remoteDataSourceImpl.fetchBreedsImages("hound") }
        assert(response.isSuccess)
        assert(response.getOrNull() != null)
    }

    @Test
    fun `test images failed response`() = coroutineDispatcher.runBlockingTest {
        coEvery {
            remoteDataSourceImpl.fetchBreedsImages(any())
        } returns Result.failure(Throwable(""))

        val response = breedRepositoryImpl.getBreedsImages("hound").last()

        coVerify { remoteDataSourceImpl.fetchBreedsImages("hound") }
        assert(response.isFailure)
        assert(response.getOrNull() == null)
    }

    @After
    fun finalize() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}