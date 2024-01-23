package com.example.masterand.repository

import com.example.masterand.model.Profile
import kotlinx.coroutines.flow.Flow

public interface ProfileRepository {
    fun getAllProfileStream(): Flow<List<Profile>>

    fun getProfileById(profileId: Long): Flow<Profile>

    fun getProfileByEmail(email: String): Flow<Profile>

    suspend fun insertProfile(profile: Profile) : Long

    suspend fun updateProfile(profile: Profile)
}