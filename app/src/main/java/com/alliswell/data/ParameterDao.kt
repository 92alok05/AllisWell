package com.alliswell.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface ParameterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( parameter: Parameter) : Long

}