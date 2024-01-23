package com.example.masterand.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class Profile (
    @PrimaryKey(autoGenerate = true)
    val id_profile: Long = 0,
    val name: String,
    val email: String
)