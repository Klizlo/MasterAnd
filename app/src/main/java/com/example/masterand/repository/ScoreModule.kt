package com.example.masterand.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ScoreModule {
    @Binds
    abstract fun bindScoreRepository(scoreRepositoryImpl: ScoreRepositoryImpl): ScoreRepository
}