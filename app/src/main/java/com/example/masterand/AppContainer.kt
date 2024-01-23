package com.example.masterand

import android.content.Context
import com.example.masterand.database.MasterAndDatabase
import com.example.masterand.repository.ProfileRepository
import com.example.masterand.repository.ProfileRepositoryImpl
import com.example.masterand.repository.ProfileWithScoreRepository
import com.example.masterand.repository.ProfileWithScoreRepositoryImpl
import com.example.masterand.repository.ScoreRepository
import com.example.masterand.repository.ScoreRepositoryImpl

interface AppContainer {
    val profileRepository: ProfileRepository
    val scoreRepository: ScoreRepository
    val profileWithScoreRepository: ProfileWithScoreRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val profileRepository: ProfileRepository by lazy {
        ProfileRepositoryImpl(MasterAndDatabase.getDatabase(context).getProfileDao())
    }
    override val scoreRepository: ScoreRepository by lazy {
        ScoreRepositoryImpl(MasterAndDatabase.getDatabase(context).getScoreDao())
    }
    override val profileWithScoreRepository: ProfileWithScoreRepository by lazy {
        ProfileWithScoreRepositoryImpl(MasterAndDatabase.getDatabase(context).getProfileWithScoreDao())
    }


}