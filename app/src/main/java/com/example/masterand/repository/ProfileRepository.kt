package com.example.masterand.repository

import com.example.masterand.model.Profile
import kotlinx.coroutines.flow.Flow

public interface ProfileRepository {
    fun getAllProfileStream(): Flow<List<Profile>>

    fun existsByEmail(email: String): Flow<Boolean>

    suspend fun insertProfile(profile: Profile)

    suspend fun updateProfile(profile: Profile)
}