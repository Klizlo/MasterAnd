package com.example.masterand.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
class Score (
    @PrimaryKey(autoGenerate = true)
    val id_score: Long = 0,
    val points: Int,
    val id_profile: Long
)