package com.example.masterand.repository

import com.example.masterand.model.ProfileWithScore
import kotlinx.coroutines.flow.Flow

interface ProfileWithScoreRepository {

    fun getAllProfileWithScores(): Flow<List<ProfileWithScore>>
}