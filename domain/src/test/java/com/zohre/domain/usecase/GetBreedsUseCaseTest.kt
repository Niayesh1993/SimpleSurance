package com.zohre.domain.usecase

import com.zohre.domain.model.Breed
import com.zohre.domain.repository.BreedRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetBreedsUseCaseTest {

    @RelaxedMockK
    lateinit var breedRepository: BreedRepository

    @InjectMockKs
    lateinit var getBreedsUseCase: GetBreedsUseCase

    private lateinit var coroutineDispatcher: TestCoroutineDispatcher

    @Before
    fun setUp(){
        coroutineDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test get breeds successful response`() = coroutineDispatcher.runBlockingTest {
        val breed = mockk<Breed>()
        every { breedRepository.getBreeds() } returns flowOf(Result.success(breed))

        val response = getBreedsUseCase.execute().first()

        coVerify { breedRepository.getBreeds() }
        Assert.assertTrue(response.isSuccess)
        assert(response.getOrNull() != null)
    }

    @Test
    fun `test get breeds failed response`() = coroutineDispatcher.runBlockingTest {
        every {
            breedRepository.getBreeds()
        }returns flowOf(Result.failure(Throwable("")), Result.failure(Throwable("")))

        val response = getBreedsUseCase.execute().last()

        coVerify { breedRepository.getBreeds() }
        assert(response.isFailure)
        Assert.assertEquals(null, response.getOrNull())

    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}