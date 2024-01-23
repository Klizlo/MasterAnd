package com.example.masterand.repository

import com.example.masterand.model.Score

interface ScoreRepository {

    suspend fun insertScore(score: Score)

}