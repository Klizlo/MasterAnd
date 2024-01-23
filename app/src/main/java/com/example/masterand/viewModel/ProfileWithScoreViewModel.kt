package com.example.masterand.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masterand.repository.ProfileWithScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileWithScoreViewModel @Inject constructor(private val profileWithScoreRepository: ProfileWithScoreRepository) : ViewModel() {
    val profileWithScore = profileWithScoreRepository
        .getAllProfileWithScores()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}