package com.alliswell.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SituationDao {
    @Query("SELECT * FROM situation where situationId in (:situationIds)")
    fun getSituations( situationIds: List<Int>): Flow<List<Situation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( situation: Situation): Long
}