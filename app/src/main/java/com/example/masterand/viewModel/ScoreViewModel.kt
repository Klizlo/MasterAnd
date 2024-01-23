package com.example.masterand.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.masterand.model.Score
import com.example.masterand.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(private val scoreRepository: ScoreRepository) : ViewModel() {
    var profileId by mutableStateOf(0L)
    var points by mutableStateOf(1)
        private set

    suspend fun insertScore() {
        scoreRepository.insertScore(Score(id_profile = profileId, points = points))
    }

    fun updatePoints(newPoints: Int) {
        points = newPoints
    }
}