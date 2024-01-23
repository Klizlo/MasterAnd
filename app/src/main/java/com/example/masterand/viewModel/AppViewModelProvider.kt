package com.example.masterand.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.masterand.MasterAndApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ProfileViewModel(masterAndApplication().container.profileRepository)
        }
        initializer {
            ScoreViewModel(masterAndApplication().container.scoreRepository)
        }
        initializer {
            ProfileWithScoreViewModel(masterAndApplication().container.profileWithScoreRepository)
        }
    }
}

fun CreationExtras.masterAndApplication(): MasterAndApplication = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MasterAndApplication
        )