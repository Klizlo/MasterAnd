package com.example.masterand.repository

import com.example.masterand.dao.ProfileDao
import com.example.masterand.model.Profile
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(private val profileDao: ProfileDao) : ProfileRepository {
    override fun getAllProfileStream(): Flow<List<Profile>> {
        return profileDao.getAllProfiles()
    }

    override fun existsByEmail(email: String): Flow<Boolean> {
        return profileDao.existsByEmail(email)
    }

    override suspend fun insertProfile(profile: Profile) {
        profileDao.insert(profile)
    }

    override suspend fun updateProfile(profile: Profile) {
        profileDao.update(profile)
    }
}