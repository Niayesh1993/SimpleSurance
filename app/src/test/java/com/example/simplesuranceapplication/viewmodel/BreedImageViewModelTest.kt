package com.example.simplesuranceapplication.viewmodel

import com.example.simplesuranceapplication.ui.breedlist.BreedViewModel
import com.example.simplesuranceapplication.ui.breedlist.states.BreedUiModel
import com.example.simplesuranceapplication.ui.breedlist.states.BreedUiState
import com.example.simplesuranceapplication.ui.imageslist.BreedImageViewModel
import com.example.simplesuranceapplication.ui.imageslist.states.BreedImageUiState
import com.example.simplesuranceapplication.utils.noBreedFound
import com.zohre.domain.model.Breed
import com.zohre.domain.model.BreedImages
import com.zohre.domain.repository.BreedRepository
import com.zohre.domain.usecase.GetBreedImagesUseCase
import com.zohre.domain.usecase.GetBreedsUseCase
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class BreedImageViewModelTest {

    @RelaxedMockK
    lateinit var breedRepository: BreedRepository

    @InjectMockKs
    lateinit var getBreedImagesUseCase: GetBreedImagesUseCase

    @InjectMockKs
    lateinit var viewModel: BreedImageViewModel

    private lateinit var coroutineDispatcher: TestCoroutineDispatcher

    @Before
    fun setUp() {
        coroutineDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test loading state`() = coroutineDispatcher.runBlockingTest {
        val result = viewModel.breedImageState.first()

        Assert.assertEquals(result, BreedImageUiState.Loading)
    }

    @Test
    fun `test successful empty breed loading`() = coroutineDispatcher.runBlockingTest {
        every {
            getBreedImagesUseCase.execute(any())
        } returns flow { emit(Result.success(BreedImages(breedImages = emptyList()))) }

        viewModel.loadBreedImages("hound")
        val result = viewModel.breedImageState.first()

        coVerify { getBreedImagesUseCase.execute("hound") }
        assert(result is BreedImageUiState.BreedImagesAvailable)
        assert((result as BreedImageUiState.BreedImagesAvailable).breedImages!!.breedImages.isEmpty())
    }

    @Test
    fun `test breed loading - success`() = coroutineDispatcher.runBlockingTest {
        val breedImage = mockk<BreedImages>()
        every { getBreedImagesUseCase.execute(any()) } returns flow {
            emit(Result.success(breedImage))
        }

        viewModel.loadBreedImages("hound")
        val result = viewModel.breedImageState.first()

        coVerify { getBreedImagesUseCase.execute("hound") }
        assert(result is BreedImageUiState.BreedImagesAvailable)
        assert((result as BreedImageUiState.BreedImagesAvailable).breedImages == breedImage)
    }

    @Test
    fun `test breed loading - failure`() = coroutineDispatcher.runBlockingTest {
        every { getBreedImagesUseCase.execute(any()) } returns flow {
            emit(Result.failure(Throwable("")))
        }

        viewModel.loadBreedImages("hound")
        val result = viewModel.breedImageState.first()

        coVerify { getBreedImagesUseCase.execute("hound") }
        assert(result is BreedImageUiState.Failure)
        assert((result as BreedImageUiState.Failure).message == noBreedFound.message)

    }


    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}