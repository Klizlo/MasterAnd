package com.example.masterand.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.masterand.dao.ProfileDao
import com.example.masterand.dao.ScoreDao
import com.example.masterand.model.Profile
import com.example.masterand.model.Score

@Database(entities = [Profile::class, Score::class], version = 1, exportSchema = false)
abstract class MasterAndDatabase: RoomDatabase() {

    abstract fun getProfileDao(): ProfileDao
    abstract fun getScoreDao(): ScoreDao
    companion object {
        fun getDatabase(current: Context): MasterAndDatabase {
            return Room
                .databaseBuilder(current, MasterAndDatabase::class.java, "masterand-database")
                .build()
        }
    }
}