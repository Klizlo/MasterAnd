package com.example.masterand.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masterand.repository.ProfileWithScoreRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ProfileWithScoreViewModel(private val profileWithScoreRepository: ProfileWithScoreRepository) : ViewModel() {
    val profileWithScore = profileWithScoreRepository
        .getAllProfileWithScores()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}