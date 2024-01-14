package com.example.masterand.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "profiles", indices = [
    Index(value = ["email"], unique = true)
])
data class Profile (
    @PrimaryKey
    val id_profile: Int = 0,
    val name: String,
    val email: String
)