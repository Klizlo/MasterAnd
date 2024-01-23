package com.example.masterand.database

import android.content.Context
import com.example.masterand.dao.ProfileDao
import com.example.masterand.dao.ProfileWithScoreDao
import com.example.masterand.dao.ScoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesProfileDao(masterAndDatabase: MasterAndDatabase): ProfileDao {
        return masterAndDatabase.getProfileDao()
    }

    @Provides
    fun providesScoreDao(masterAndDatabase: MasterAndDatabase): ScoreDao {
        return masterAndDatabase.getScoreDao()
    }

    @Provides
    fun providesProfileWithScoreDao(masterAndDatabase: MasterAndDatabase): ProfileWithScoreDao {
        return masterAndDatabase.getProfileWithScoreDao()
    }

    @Provides
    @Singleton
    fun provideMasterAndDatabase(@ApplicationContext applicationContext: Context): MasterAndDatabase {
        return MasterAndDatabase.getDatabase(applicationContext)
    }
}