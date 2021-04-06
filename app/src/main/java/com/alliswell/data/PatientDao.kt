package com.alliswell.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Query("SELECT * FROM patient ORDER BY first_name ASC")
    fun getAll()  : Flow<List<Patient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( patient: Patient)

    @Query("DELETE FROM patient")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM patient")
    fun getCount() : Int

}