package com.example.simplesuranceapplication.viewmodel

import com.example.simplesuranceapplication.ui.breedlist.BreedViewModel
import com.example.simplesuranceapplication.ui.breedlist.states.BreedUiState
import com.example.simplesuranceapplication.utils.noBreedFound
import com.zohre.domain.model.Breed
import com.zohre.domain.repository.BreedRepository
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
class BreedViewModelTest {

    @RelaxedMockK
    lateinit var breedRepository: BreedRepository

    @InjectMockKs
    lateinit var getBreedsUseCase: GetBreedsUseCase

    @InjectMockKs
    lateinit var viewModel: BreedViewModel

    private lateinit var coroutineDispatcher: TestCoroutineDispatcher

    @Before
    fun setUp() {
        coroutineDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test loading state`() = coroutineDispatcher.runBlockingTest {
        val result = viewModel.breedState.first()

        Assert.assertEquals(result, BreedUiState.Loading)
    }

    @Test
    fun `test successful empty breed loading`() = coroutineDispatcher.runBlockingTest {
        every {
            getBreedsUseCase.execute()
        } returns flow {emit(Result.success(Breed(mapOf())))}

        viewModel.fetchBreedList()
        val result = viewModel.breedState.first()

        coVerify { getBreedsUseCase.execute() }
        assert(result is BreedUiState.BreedAvailable)
        assert((result as BreedUiState.BreedAvailable).breeds!!.data!!.isEmpty())
    }

    @Test
    fun `test breed loading - success`() = coroutineDispatcher.runBlockingTest {
        val breed = mockk<Breed>()
        every { getBreedsUseCase.execute() } returns flow {
            emit(Result.success(breed))
        }

        viewModel.fetchBreedList()
        val result = viewModel.breedState.first()

        coVerify { getBreedsUseCase.execute() }
        assert(result is BreedUiState.BreedAvailable)
        assert((result as BreedUiState.BreedAvailable).breeds == breed)
    }

    @Test
    fun `test breed loading - failure`() = coroutineDispatcher.runBlockingTest {
        every { getBreedsUseCase.execute() } returns flow {
            emit(Result.failure(Throwable("")))
        }

        viewModel.fetchBreedList()
        val result = viewModel.breedState.first()

        coVerify { getBreedsUseCase.execute() }
        assert(result is BreedUiState.Failure)
        assert((result as BreedUiState.Failure).message == noBreedFound.message)

    }


    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}