package com.zohre.data.di

import com.zohre.data.datasource.BreedRemoteDataSource
import com.zohre.data.datasource.BreedRemoteDataSourceImpl
import com.zohre.data.repository.BreedRepositoryImpl
import com.zohre.domain.repository.BreedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class BreedDataModule {

    @Binds
    abstract fun bindRemoteSource(
        breedRemoteDataSourceImpl: BreedRemoteDataSourceImpl
    ): BreedRemoteDataSource

    @Binds
    abstract fun bindBreedRepository(
        breedRepositoryImpl: BreedRepositoryImpl
    ): BreedRepository
}