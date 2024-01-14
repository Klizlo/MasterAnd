package com.example.masterand.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.masterand.model.Score

@Dao
interface ScoreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertScore(score: Score)

    @Update
    fun updateScore(score: Score)
}