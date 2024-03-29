package com.example.masterand

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MasterAndApplication: Application() {
    lateinit var container: AppDataContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}