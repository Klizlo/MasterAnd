package com.example.masterand.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProfileWithScores (
    @Embedded
    val profile: Profile,
    @Relation(
        parentColumn = "id_profile",
        entityColumn = "profile_id"
    )
    val scores: List<Score>
)