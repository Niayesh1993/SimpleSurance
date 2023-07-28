package com.zohre.domain.usecase

import com.zohre.domain.model.Breed
import com.zohre.domain.model.BreedImages
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
class GetBreedImagesUseCaseTest {

    @RelaxedMockK
    lateinit var breedRepository: BreedRepository

    @InjectMockKs
    lateinit var getBreedImagesUseCase: GetBreedImagesUseCase

    private lateinit var coroutineDispatcher: TestCoroutineDispatcher

    @Before
    fun setUp(){
        coroutineDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test get images successful response`() = coroutineDispatcher.runBlockingTest {
        val breed = mockk<BreedImages>()
        every { breedRepository.getBreedsImages(any()) } returns flowOf(Result.success(breed))

        val response = getBreedImagesUseCase.execute("hound").first()

        coVerify { breedRepository.getBreedsImages("hound") }
        Assert.assertTrue(response.isSuccess)
        assert(response.getOrNull() != null)
    }

    @Test
    fun `test get images failed response`() = coroutineDispatcher.runBlockingTest {
        every {
            breedRepository.getBreedsImages(any())
        } returns flowOf(Result.failure(Throwable("")), Result.failure(Throwable("")))

        val response = getBreedImagesUseCase.execute("hound").last()

        coVerify { breedRepository.getBreedsImages("hound") }
        assert(response.isFailure)
        Assert.assertEquals(null, response.getOrNull())

    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}