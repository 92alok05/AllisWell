package com.alliswell.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface PatientSituationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( patientSituation: PatientSituation)
}