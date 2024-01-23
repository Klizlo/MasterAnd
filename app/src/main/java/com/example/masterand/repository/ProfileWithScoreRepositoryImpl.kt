package com.example.masterand.repository

import com.example.masterand.dao.ProfileWithScoreDao
import com.example.masterand.model.ProfileWithScore
import kotlinx.coroutines.flow.Flow

class ProfileWithScoreRepositoryImpl(private val profileWithScoreDao: ProfileWithScoreDao) : ProfileWithScoreRepository {
    override fun getAllProfileWithScores(): Flow<List<ProfileWithScore>> {
        return profileWithScoreDao.getPlayersWithScore()
    }
}