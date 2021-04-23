package com.alliswell.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface SituationWithParameterDao {
    @Query("SELECT * FROM situation where situationId = :situationId")
    fun getSituationWithParameters(situationId: Int) : Flow<List<SituationWithParameters>>
}