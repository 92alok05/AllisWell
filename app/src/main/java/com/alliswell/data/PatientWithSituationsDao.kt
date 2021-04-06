package com.alliswell.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientWithSituationsDao {
    @Query("SELECT * FROM patient where patientId = :patientId")
    fun getPatientWithSituations(patientId: Int) : Flow<List<PatientWithSituations>>
}