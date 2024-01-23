package com.example.masterand.repository

import com.example.masterand.dao.ProfileDao
import com.example.masterand.model.Profile
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(private val profileDao: ProfileDao) : ProfileRepository {
    override fun getAllProfileStream(): Flow<List<Profile>> {
        return profileDao.getAllProfiles()
    }

    override fun getProfileById(profileId: Long): Flow<Profile> {
        return profileDao.getProfileById(profileId)
    }

    override fun getProfileByEmail(email: String): Flow<Profile> {
        return profileDao.getProfileByEmail(email)
    }

    override suspend fun insertProfile(profile: Profile) : Long {
        return profileDao.insert(profile)
    }

    override suspend fun updateProfile(profile: Profile) {
        profileDao.update(profile)
    }
}