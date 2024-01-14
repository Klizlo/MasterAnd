package com.example.masterand.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.masterand.repository.ProfileRepository
import java.lang.IllegalArgumentException

class ProfileVieModelFactory(private val repository: ProfileRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Nieznana klasa ViewModel")
    }
}