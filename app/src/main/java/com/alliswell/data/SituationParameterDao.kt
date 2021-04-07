package com.alliswell.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface SituationParameterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( situationParameter: SituationParameter)
}