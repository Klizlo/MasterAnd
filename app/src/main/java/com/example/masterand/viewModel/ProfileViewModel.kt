package com.example.masterand.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masterand.model.Profile
import com.example.masterand.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(private val profileRepository: ProfileRepository): ViewModel() {
    lateinit var profile: Profile

    fun insertProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.insertProfile(profile = profile)
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

//    fun existsByEmail(email: String) : Boolean {
//        val result = viewModelScope.launch {
//            return profileRepository.existsByEmail(email).first()
//        }
//    }
}