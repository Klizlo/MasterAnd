package com.example.masterand.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masterand.model.Profile
import com.example.masterand.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository): ViewModel() {
    var profileId by mutableStateOf(0L)
        private set
    var name by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set

    fun updateProfileId(newProfileId: Long) {
        profileId = newProfileId
    }

    fun updateName(newName: String) {
        name = newName
    }

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    suspend fun saveProfile() {
        try {
            val profile = profileRepository.getProfileByEmail(email).first()
            val profileToUpdate = Profile(profile.id_profile, profile.name, profile.email)
            profileRepository.updateProfile(profileToUpdate)
            profileId = profile.id_profile
        } catch (npe: NullPointerException) {
            val profile = Profile(name = name, email = email)
            profileId = profileRepository.insertProfile(profile)
        }
    }

    fun getAllProfiles(): List<Profile> {
        var allProfiles : List<Profile> = emptyList()
        viewModelScope.launch {
            val allProfileStream = profileRepository.getAllProfileStream()
            allProfiles = allProfileStream.first()
        }
        return allProfiles
    }

    suspend fun getProfileById(profileId: Long) {
        val profile = profileRepository.getProfileById(profileId).first()
        this.profileId = profile.id_profile
        this.name = profile.name
        this.email = profile.email
    }

}