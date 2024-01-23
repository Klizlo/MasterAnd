package com.example.masterand.repository

import com.example.masterand.dao.ScoreDao
import com.example.masterand.model.Score

class ScoreRepositoryImpl(private val scoreDao: ScoreDao) : ScoreRepository {
    override suspend fun insertScore(score: Score) {
        scoreDao.insertScore(score)
    }
}