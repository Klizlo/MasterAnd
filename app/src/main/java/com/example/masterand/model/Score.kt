package com.example.masterand.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
class Score (
    @PrimaryKey
    val id_score: Int = 0,
    val points: Int,
    val profile_id: Int
)