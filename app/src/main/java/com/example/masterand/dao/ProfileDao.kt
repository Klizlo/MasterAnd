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

    @Query("select exists(select * from profiles where email = :email)")
    fun existsByEmail(email: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(profile: Profile)

    @Update
    suspend fun update(profile: Profile)
}