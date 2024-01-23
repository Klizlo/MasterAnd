package com.example.masterand.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.masterand.model.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Query("select * from profiles")
    fun getAllProfiles(): Flow<List<Profile>>

    @Query("select * from profiles where id_profile = :profileId")
    fun getProfileById(profileId: Long): Flow<Profile>

    @Query("select * from profiles where email = :email")
    fun getProfileByEmail(email: String): Flow<Profile>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(profile: Profile) : Long

    @Update
    suspend fun update(profile: Profile)
}