package com.example.masterand.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProfileWithScore (
    val id_profile: Long,
    val id_score: Long,
    val points: Int,
    val name: String,
    val email: String
)