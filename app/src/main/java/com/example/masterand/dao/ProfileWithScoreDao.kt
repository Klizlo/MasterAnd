package com.example.masterand.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.masterand.model.ProfileWithScore
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileWithScoreDao {

    @Query("select profiles.id_profile as id_profile, scores.id_score as id_score, scores.points as points," +
            "profiles.name as name, profiles.email as email " +
            "from profiles, scores where profiles.id_profile = scores.id_profile")
    fun getPlayersWithScore(): Flow<List<ProfileWithScore>>

}