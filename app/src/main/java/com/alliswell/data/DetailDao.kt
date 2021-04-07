package com.alliswell.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailDao {
    @Query("SELECT * FROM Detail where patientId=:patientId and situationId= :situationId ORDER BY date ASC")
    fun getAllValuesForPatientAndSituation(patientId: Int, situationId: Int)  : Flow<List<Detail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( detail: Detail)
}